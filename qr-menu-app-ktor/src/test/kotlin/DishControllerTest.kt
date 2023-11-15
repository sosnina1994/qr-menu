import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.sosninanv.api.v1.models.*
import ru.sosninanv.qrmenu.app.moduleJvm
import kotlin.test.assertEquals

class DishControllerTest {

    @Test
    fun `test create`() = testApplication {
        application(Application::moduleJvm)
        val client = myClient()

        val response = client.post("/v1/dish/create") {
            val requestObj = DishCreateRequest(
                requestId = "1234",
                debug = DishDebug(
                    mode = EDishRequestDebugMode.TEST,
                    stub = EDishRequestDebugStubs.SUCCESS,
                ),
                dish = DishCreateObject(
                    name = "name",
                    description = "desc",
                    cost = 500.5,
                    dishType = EDishType.MAIN,
                    visibility = EDishVisibility.PUBLIC,
                ),
            )

            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObject = response.body<DishCreateResponse>()

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("name", responseObject.dish?.name)
    }

    @Test
    fun `test read`() = testApplication {
        application(Application::moduleJvm)
        val client = myClient()

        val response = client.post("/v1/dish/read") {
            val requestObj = DishReadRequest(
                requestId = "12345",
                dish = DishReadObject("1"),
                debug = DishDebug(
                    mode = EDishRequestDebugMode.TEST,
                    stub = EDishRequestDebugStubs.SUCCESS,
                ),
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<DishReadResponse>()
        assertEquals(HttpStatusCode.OK, response.status)
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