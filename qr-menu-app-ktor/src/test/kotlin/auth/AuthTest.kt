package auth

import DishRepoInMemory
import DishRepoStub
import QrMenuCorSettings
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.testing.*
import org.junit.Test
import repo.IDishRepository
import ru.sosninanv.qrmenu.app.QrMenuAppSettings
import ru.sosninanv.qrmenu.app.configs.AuthConfig
import ru.sosninanv.qrmenu.app.moduleJvm
import kotlin.test.assertEquals

class AuthTest {
    @Test
    fun invalidAudience() = testApplication {
        application {
            moduleJvm(testSettings())
        }

        val response = client.post("/v1/dish/create") {
            addAuth(config = AuthConfig.TEST.copy(audience = "invalid"))
        }
        assertEquals(401, response.status.value)
    }

}

fun HttpRequestBuilder.addAuth(
    id: String? = "test",
    groups: List<String> = listOf("TEST"),
    config: AuthConfig
) {
    val token = JWT.create()
        .withAudience(config.audience)
        .withIssuer(config.issuer)
        .withClaim(AuthConfig.GROUPS_CLAIM, groups)
        .withClaim(AuthConfig.ID_CLAIM, id)
        .sign(Algorithm.HMAC256(config.secret))

    header(HttpHeaders.Authorization, "Bearer $token")
}


fun testSettings(repo: IDishRepository? = null) = QrMenuAppSettings(
    corSettings = QrMenuCorSettings(
        repoStub = DishRepoStub(),
        repoTest = repo ?: DishRepoInMemory(),
        repoProd = repo ?: DishRepoInMemory(),
    )
)

