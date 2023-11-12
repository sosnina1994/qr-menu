import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import models.*
import org.junit.Test
import ru.sosninanv.api.v1.models.*
import ru.sosninanv.qrmenu.app.QrMenuAppSettings
import ru.sosninanv.qrmenu.app.moduleJvm
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class DishInMemoryApiTest {
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val uuidSup = "10000000-0000-0000-0000-000000000003"
    private val initDish = QrMenuDishStub.prepareResult {
        id = QrMenuDishId(uuidOld)
        name = "abc"
        description = "abc"
        cost = 0.0
        type = EQrMenuDishType.DESSERT
        ownerId = QrMenuUserId("abc-Abc")
        visibility = EQrMenuVisibility.PUBLIC
        lock = QrMenuDishLock(uuidOld)
    }

    @Test
    fun create() = testApplication {
        val repo = DishRepoInMemory(initObjects = listOf(initDish), randomUuid = { uuidNew })
        application {
            moduleJvm(QrMenuAppSettings(corSettings = QrMenuCorSettings(repoTest = repo)))
        }

        val client = myClient()

        val createDish = DishCreateObject(
            name = "qwer",
            description = "qwer",
            cost = 0.0,
            dishType = EDishType.DESSERT,
            visibility = EDishVisibility.PUBLIC
        )

        val response = client.post("/v1/dish/create") {
            val requestObj = DishCreateRequest(
                requestId = "12345",
                dish = createDish,
                debug = DishDebug(
                    mode = EDishRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishCreateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidNew, responseObj.dish?.id)
        assertEquals(createDish.name, responseObj.dish?.name)
        assertEquals(createDish.description, responseObj.dish?.description)
        assertEquals(createDish.dishType, responseObj.dish?.dishType)
        assertEquals(createDish.visibility, responseObj.dish?.visibility)
    }

    @Test
    fun read() = testApplication {
        val repo = DishRepoInMemory(initObjects = listOf(initDish), randomUuid = { uuidNew })
        application {
            moduleJvm(QrMenuAppSettings(corSettings = QrMenuCorSettings(repoTest = repo)))
        }

        val client = myClient()

        val response = client.post("/v1/dish/read") {
            val requestObj = DishReadRequest(
                requestId = "12345",
                dish = DishReadObject(uuidOld),
                debug = DishDebug(
                    mode = EDishRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.dish?.id)
    }

    @Test
    fun update() = testApplication {
        val repo = DishRepoInMemory(initObjects = listOf(initDish), randomUuid = { uuidNew })
        application {
            moduleJvm(QrMenuAppSettings(corSettings = QrMenuCorSettings(repoTest = repo)))
        }
        val client = myClient()

        val dishUpdate = DishUpdateObject(
            id = uuidOld,
            name = "qwer1",
            description = "qwer",
            cost = 0.0,
            dishType = EDishType.DESSERT,
            visibility = EDishVisibility.PUBLIC,
            lock = initDish.lock.asString()
        )

        val response = client.post("/v1/dish/update") {
            val requestObj = DishUpdateRequest(
                requestId = "12345",
                dish = dishUpdate,
                debug = DishDebug(
                    mode = EDishRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(dishUpdate.id, responseObj.dish?.id)
        assertEquals(dishUpdate.name, responseObj.dish?.name)
        assertEquals(dishUpdate.description, responseObj.dish?.description)
        assertEquals(dishUpdate.dishType, responseObj.dish?.dishType)
        assertEquals(dishUpdate.visibility, responseObj.dish?.visibility)
    }

    @Test
    fun delete() = testApplication {
        val repo = DishRepoInMemory(initObjects = listOf(initDish), randomUuid = { uuidNew })
        application {
            moduleJvm(QrMenuAppSettings(corSettings = QrMenuCorSettings(repoTest = repo)))
        }
        val client = myClient()

        val response = client.post("/v1/dish/delete") {

            val requestObj = DishDeleteRequest(
                requestId = "12345",
                dish = DishDeleteObject(
                    id = uuidOld,
                    lock = initDish.lock.asString()
                ),
                debug = DishDebug(
                    mode = EDishRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals(uuidOld, responseObj.dish?.id)
    }

    @Test
    fun search() = testApplication {
        val repo = DishRepoInMemory(initObjects = listOf(initDish), randomUuid = { uuidNew })
        application {
            moduleJvm(QrMenuAppSettings(corSettings = QrMenuCorSettings(repoTest = repo)))
        }
        val client = myClient()

        val response = client.post("/v1/dish/search") {
            val requestObj = DishSearchRequest(
                requestId = "12345",
                dishFilter = DishSearchFilter(),
                debug = DishDebug(
                    mode = EDishRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.dishes?.size)
        assertEquals(uuidOld, responseObj.dishes?.first()?.id)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }

}
