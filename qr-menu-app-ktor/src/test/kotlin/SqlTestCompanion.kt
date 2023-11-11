/*
import com.benasher44.uuid.uuid4
import models.QrMenuDish
import org.testcontainers.containers.PostgreSQLContainer
import ru.sosninanv.qrmenu.repo.postgresql.RepoDishSQL
import ru.sosninanv.qrmenu.repo.postgresql.SqlProperties
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "password"
    private const val SCHEMA = "menu"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
        }
    }

    fun start() {
        container.start()
    }

    fun stop() {
        container.stop()
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        test: String,
        initObjects: Collection<QrMenuDish> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): RepoDishSQL {
        return RepoDishSQL(
            properties = SqlProperties(
                url = url,
                user = USER,
                password = PASS,
                schema = SCHEMA,
                table = "Dish-$test",
            ),
            initObjects = initObjects,
            randomUuid = randomUuid
        )
    }
}
*/
