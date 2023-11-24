import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.junit.Test
import ru.sosninanv.qrmenu.app.QrMenuAppSettings
import ru.sosninanv.qrmenu.app.moduleJvm
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun `init app`() = testApplication {
        val repo = DishRepoInMemory()
        application {
            moduleJvm(QrMenuAppSettings(corSettings = QrMenuCorSettings(repoTest = repo)))
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, this.status)
            assertEquals("OK", this.bodyAsText())
        }
    }

}
