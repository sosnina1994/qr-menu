import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.QrMenuDish
import models.QrMenuDishId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import repo.DbDishIdRequest
import repo.IDishRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDishReadTest {
    abstract val repo: IDishRepository
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readDish(DbDishIdRequest(readSucc.id))

        assertEquals(true, result.isSuccess)
        assertEquals(readSucc, result.data)
        assertTrue(result.errors.isEmpty())
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readDish(DbDishIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitDishes("read") {
        override val initObjects: List<QrMenuDish> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = QrMenuDishId("ad-repo-read-notFound")

    }
}
