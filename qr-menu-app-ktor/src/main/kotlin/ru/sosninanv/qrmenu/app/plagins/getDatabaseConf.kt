package ru.sosninanv.qrmenu.app.plagins

import DishRepoInMemory
import io.ktor.server.application.*
import repo.IDishRepository
import ru.sosninanv.qrmenu.app.configs.ConfigPaths
import ru.sosninanv.qrmenu.app.configs.PostgresConfig
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

fun Application.getDatabaseConf(type: DbType): IDishRepository {
    val dbSettingPath = "${ConfigPaths.repository}.${type.confName}"
    val dbSetting = environment.config.propertyOrNull(dbSettingPath)?.getString()?.lowercase()
    return when (dbSetting) {
        "in-memory", "inmemory", "memory", "mem" -> initInMemory()
        //"postgres", "postgresql", "pg", "sql", "psql" -> initPostgres()
        else -> throw IllegalArgumentException(
            "$dbSettingPath must be set in application.yml to one of: " +
                    "'inmemory', 'postgres', 'cassandra', 'gremlin'"
        )
    }
}

private fun Application.initInMemory(): IDishRepository {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return DishRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}

/*private fun Application.initPostgres(): IDishRepository {
    val config = PostgresConfig(environment.config)
    return RepoAdSQL(
        properties = SqlProperties(
            url = config.url,
            user = config.user,
            password = config.password,
            schema = config.schema,
        )
    )
}*/

enum class DbType(val confName: String) {
    PROD("prod"), TEST("test")
}