package ru.sosninanv.qrmenu.repo.postgresql

import models.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class DishTable(tableName: String = "dishes") : Table(tableName) {
    val id = varchar("id", 128)
    val name = varchar("name", 128)
    val description = text("description")
    val cost = double("cost")
    val type = enumeration("type", EQrMenuDishType::class)
    val visibility = enumeration("visibility", EQrMenuVisibility::class)
    val owner = varchar("owner", 128)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = QrMenuDish(
        id = QrMenuDishId(res[id].toString()),
        name = res[name],
        description = res[description],
        cost = res[cost],
        type = res[type],
        visibility = res[visibility],
        ownerId = QrMenuUserId(res[owner].toString()),
        lock = QrMenuDishLock(res[lock])
    )


    fun to(it: UpdateBuilder<*>, dish: QrMenuDish, randomUuid: () -> String) {
        it[id] = dish.id.takeIf { it != QrMenuDishId.NONE }?.asString() ?: randomUuid()
        it[name] = dish.name
        it[description] = dish.description
        it[cost] = dish.cost
        it[type] = dish.type
        it[visibility] = dish.visibility
        it[owner] = dish.ownerId.asString()
        it[lock] = dish.lock.takeIf { it != QrMenuDishLock.NONE }?.asString() ?: randomUuid()
    }

}
