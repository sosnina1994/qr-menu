import SqlTestCompanion.repoUnderTestContainer
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import models.*
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import ru.sosninanv.api.v1.models.*
import ru.sosninanv.qrmenu.app.QrMenuAppSettings
import ru.sosninanv.qrmenu.app.moduleJvm
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class DishPostgresApiTest {
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

    private val initDishSupply = QrMenuDishStub.prepareResult {
        id = QrMenuDishId(uuidSup)
        name = "abc"
        description = "abc"
        cost = 0.0
        type = EQrMenuDishType.DESSERT
        ownerId = QrMenuUserId("abc-Abc")
        visibility = EQrMenuVisibility.PUBLIC
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun tearUp() {
            SqlTestCompanion.start()
        }

        @AfterClass
        @JvmStatic
        fun tearDown() {
            SqlTestCompanion.stop()
        }
    }

    @Test
    fun create() = testApplication {
        val repo = repoUnderTestContainer(test = "create", initObjects = listOf(initDish), randomUuid = { uuidNew })

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

        val response = client.post("/v1/ad/create") {
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
