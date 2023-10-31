import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import models.*
import repo.*
import java.util.Collections.emptyList
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class DishRepoInMemory(
    initObjects: Collection<QrMenuDish> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IDishRepository {

    private val cache = Cache.Builder<String, DishEntity>()
        .expireAfterWrite(ttl)
        .build()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(dish: QrMenuDish) {
        val entity = DishEntity(dish)
        if (entity.id == null) {
            return
        }
        cache.put(entity.id, entity)
    }

    override suspend fun createDish(rq: DbDishRequest): DbDishResponse {
        val key = randomUuid()
        val dish = rq.dish.copy(id = QrMenuDishId(key), lock = QrMenuDishLock(randomUuid()))
        val entity = DishEntity(dish)
        cache.put(key, entity)
        return DbDishResponse(
            data = dish,
            isSuccess = true,
        )
    }

    override suspend fun readDish(rq: DbDishIdRequest): DbDishResponse {
        val key = rq.id.takeIf { it != QrMenuDishId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbDishResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateDish(rq: DbDishRequest): DbDishResponse {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDish(rq: DbDishIdRequest): DbDishResponse {
        TODO("Not yet implemented")
    }

    override suspend fun searchDish(rq: DbDishFilterRequest): DbDishesResponse {
        TODO("Not yet implemented")
    }

    companion object {
        val resultErrorEmptyId = DbDishResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                QrMenuError(
                    code = "id-empty",
                    group = "validation",
                    field = "id",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbDishResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                QrMenuError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbDishResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                QrMenuError(
                    code = "not-found",
                    field = "id",
                    message = "Not Found"
                )
            )
        )
    }

}
