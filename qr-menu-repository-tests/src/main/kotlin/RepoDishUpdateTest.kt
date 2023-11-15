import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import repo.DbDishRequest
import repo.IDishRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDishUpdateTest {
    abstract val repo: IDishRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateIdNotFound = QrMenuDishId("ad-repo-update-not-found")
    protected val lockBad = QrMenuDishLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = QrMenuDishLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        QrMenuDish(
            id = updateSucc.id,
            name = "update object",
            description = "update object description",
            cost = 100.0,
            type = EQrMenuDishType.DESSERT,
            ownerId = QrMenuUserId("owner-123"),
            visibility = EQrMenuVisibility.PUBLIC,
            lock = initObjects.first().lock
        )
    }

    private val reqUpdateNotFound = QrMenuDish(
        id = updateIdNotFound,
        name = "update object not found",
        description = "update object not found description",
        cost = 100.0,
        type = EQrMenuDishType.DESSERT,
        ownerId = QrMenuUserId("owner-123"),
        visibility = EQrMenuVisibility.PUBLIC,
        lock = initObjects.first().lock
    )

    private val reqUpdateConc by lazy {
        QrMenuDish(
            id = updateConc.id,
            name = "update object not found",
            description = "update object not found description",
            cost = 100.0,
            type = EQrMenuDishType.DESSERT,
            ownerId = QrMenuUserId("owner-123"),
            visibility = EQrMenuVisibility.PUBLIC,
            lock = lockBad
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateDish(DbDishRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.name, result.data?.name)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(reqUpdateSucc.type, result.data?.type)
        assertTrue(result.errors.isEmpty())
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateDish(DbDishRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateDish(DbDishRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitDishes("update") {
        override val initObjects: List<QrMenuDish> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
