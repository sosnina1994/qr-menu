import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import models.DishEntity
import models.QrMenuDish
import models.QrMenuDishId
import models.QrMenuDishLock
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

    override suspend fun createDish(req: DbDishRequest): DbDishResponse {
        val key = randomUuid()
        val dish = req.dish.copy(id = QrMenuDishId(key), lock = QrMenuDishLock(randomUuid()))
        val entity = DishEntity(dish)
        cache.put(key, entity)
        return DbDishResponse(
            data = dish,
            isSuccess = true,
        )
    }

    override suspend fun readDish(rq: DbDishIdRequest): DbDishResponse {
        TODO("Not yet implemented")
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

}
