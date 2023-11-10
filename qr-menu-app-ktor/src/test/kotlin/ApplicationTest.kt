import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.sosninanv.qrmenu.app.QrMenuAppSettings
import ru.sosninanv.qrmenu.app.moduleJvm
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun `init app`() = testApplication {
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, this.status)
            assertEquals("OK", this.bodyAsText())
        }
    }

}