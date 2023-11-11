package ru.sosninanv.qrmenu.repo.postgresql

import com.benasher44.uuid.uuid4
import helpers.asQrMenuError
import models.QrMenuDish
import models.QrMenuDishId
import models.QrMenuDishLock
import models.QrMenuUserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import repo.*

class RepoDishSQL(
    properties: SqlProperties,
    initObjects: Collection<QrMenuDish> = emptyList(),
    val randomUuid: () -> String = { uuid4().toString() },
) : IDishRepository {
    private val dishTable = DishTable(properties.table)

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    init {
        transaction(conn) {
            SchemaUtils.create(dishTable)
            initObjects.forEach { createDish(it) }
        }
    }

    private fun createDish(dish: QrMenuDish): QrMenuDish {
        val res = dishTable
            .insert {
                to(it, dish, randomUuid)
            }
            .resultedValues
            ?.map { dishTable.from(it) }
        return res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private fun read(id: QrMenuDishId): DbDishResponse {
        val res = dishTable.select {
            dishTable.id eq id.asString()
        }.singleOrNull() ?: return DbDishResponse.errorNotFound
        return DbDishResponse.success(dishTable.from(res))
    }

    private fun update(
        id: QrMenuDishId,
        lock: QrMenuDishLock,
        block: (QrMenuDish) -> DbDishResponse
    ): DbDishResponse =
        transactionWrapper {
            if (id == QrMenuDishId.NONE) return@transactionWrapper DbDishResponse.errorEmptyId

            val current = dishTable.select { dishTable.id eq id.asString() }
                .singleOrNull()
                ?.let { dishTable.from(it) }

            when {
                current == null -> DbDishResponse.errorNotFound
                current.lock != lock -> DbDishResponse.errorConcurrent(lock, current)
                else -> block(current)
            }
        }

    private fun <T> transactionWrapper(block: () -> T, handle: (Exception) -> T): T =
        try {
            transaction(conn) {
                block()
            }
        } catch (e: Exception) {
            handle(e)
        }

    private fun transactionWrapper(block: () -> DbDishResponse): DbDishResponse =
        transactionWrapper(block) { DbDishResponse.error(it.asQrMenuError()) }

    override suspend fun createDish(rq: DbDishRequest): DbDishResponse = transactionWrapper {
        DbDishResponse.success(createDish(rq.dish))
    }

    override suspend fun readDish(rq: DbDishIdRequest): DbDishResponse = transactionWrapper { read(rq.id) }

    override suspend fun updateDish(rq: DbDishRequest): DbDishResponse = update(rq.dish.id, rq.dish.lock) {
        dishTable.update({ dishTable.id eq rq.dish.id.asString() }) {
            to(it, rq.dish.copy(lock = QrMenuDishLock(randomUuid())), randomUuid)
        }
        read(rq.dish.id)
    }

    override suspend fun deleteDish(rq: DbDishIdRequest): DbDishResponse = update(rq.id, rq.lock) {
        dishTable.deleteWhere { id eq rq.id.asString() }
        DbDishResponse.success(it)
    }

    override suspend fun searchDish(rq: DbDishFilterRequest): DbDishesResponse =

        transactionWrapper({
            val res = dishTable.select {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != QrMenuUserId.NONE) {
                        add(dishTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (dishTable.name like "%${rq.titleFilter}%")
                                    or (dishTable.description like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbDishesResponse(data = res.map { dishTable.from(it) }, isSuccess = true)
        }, {
            DbDishesResponse.error(it.asQrMenuError())
        })

}
