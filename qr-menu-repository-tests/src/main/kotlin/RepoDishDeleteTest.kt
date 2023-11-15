import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.QrMenuDish
import models.QrMenuDishId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import repo.DbDishIdRequest
import repo.IDishRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDishDeleteTest {
    abstract val repo: IDishRepository
    protected open val deleteSucc = initObjects[0]
    protected open val deleteConc = initObjects[1]

    @Test
    fun deleteSuccess() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteDish(DbDishIdRequest(deleteSucc.id, lock = lockOld))

        assertEquals(true, result.isSuccess)
        assertTrue(result.errors.isEmpty())
        assertEquals(lockOld, result.data?.lock)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readDish(DbDishIdRequest(notFoundId, lock = lockOld))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val lockOld = deleteSucc.lock
        val result = repo.deleteDish(DbDishIdRequest(deleteConc.id, lock = lockBad))

        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(lockOld, result.data?.lock)
    }

    companion object : BaseInitDishes("delete") {
        override val initObjects: List<QrMenuDish> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
        val notFoundId = QrMenuDishId("ad-repo-delete-notFound")
    }
}
