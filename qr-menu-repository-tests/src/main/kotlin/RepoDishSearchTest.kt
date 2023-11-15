import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.EQrMenuDishType
import models.QrMenuDish
import models.QrMenuUserId
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import repo.DbDishFilterRequest
import repo.IDishRepository


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDishSearchTest {
    abstract val repo: IDishRepository

    protected open val initializedObjects: List<QrMenuDish> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchDish(DbDishFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertTrue(result.errors.isEmpty())
    }

    @Test
    fun searchType() = runRepoTest {
        val result = repo.searchDish(DbDishFilterRequest(dishType = EQrMenuDishType.DESSERT))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertTrue(result.errors.isEmpty())
    }

    companion object: BaseInitDishes("search") {

        val searchOwnerId = QrMenuUserId("owner-124")
        override val initObjects: List<QrMenuDish> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad3", type = EQrMenuDishType.DESSERT),
            createInitTestModel("ad4", ownerId = searchOwnerId),
            createInitTestModel("ad5", type = EQrMenuDishType.DESSERT),
        )
    }
}
