import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.*
import org.junit.Assert.*
import org.junit.Test
import repo.DbDishRequest
import repo.IDishRepository

@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDishCreateTest {
    abstract val repo: IDishRepository

    protected open val lockNew: QrMenuDishLock = QrMenuDishLock("20000000-0000-0000-0000-000000000002")

    private val createObj = QrMenuDish(
        name = "create object",
        description = "create object description",
        ownerId = QrMenuUserId("owner-123"),
        type = EQrMenuDishType.DESSERT,
        visibility = EQrMenuVisibility.PUBLIC,
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createDish(DbDishRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: QrMenuDishId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.name, result.data?.name)
        assertEquals(expected.description, result.data?.description)
        assertEquals(expected.type, result.data?.type)
        assertNotEquals(QrMenuDishId.NONE, result.data?.id)
        assertTrue(result.errors.isEmpty())
    }

    companion object : BaseInitDishes("create") {
        override val initObjects: List<QrMenuDish> = emptyList()
    }
}
