package ru.sosninanv.qrmenu.repo.postgresql

import com.benasher44.uuid.uuid4
import models.QrMenuDish
import org.testcontainers.containers.PostgreSQLContainer
import java.time.Duration

class PostgresContainer : PostgreSQLContainer<PostgresContainer>("postgres:13.2")

object SqlTestCompanion {
    private const val USER = "postgres"
    private const val PASS = "marketplace-pass"
    private const val SCHEMA = "marketplace"

    private val container by lazy {
        PostgresContainer().apply {
            withUsername(USER)
            withPassword(PASS)
            withDatabaseName(SCHEMA)
            withStartupTimeout(Duration.ofSeconds(300L))
            start()
        }
    }

    private val url: String by lazy { container.jdbcUrl }

    fun repoUnderTestContainer(
        test: String,
        initObjects: Collection<QrMenuDish> = emptyList(),
        randomUuid: () -> String = { uuid4().toString() },
    ): RepoDishSQL {
        return RepoDishSQL(
            SqlProperties(
                url = url,
                user = USER,
                password = PASS,
                schema = SCHEMA,
                table = "t_dishes_$test",
            ),
            initObjects,
            randomUuid = randomUuid
        )
    }
}