package ru.sosninanv.qrmenu.app.configs

import io.ktor.server.config.*

data class PostgresConfig(
    val url: String = "jdbc:postgresql://localhost:5432/menu",
    val user: String = "postgres",
    val password: String = "password",
    val schema: String = "menu",
) {
    constructor(config: ApplicationConfig): this(
        url = config.property("$PATH.url").getString(),
        user = config.property("$PATH.user").getString(),
        password = config.property("$PATH.password").getString(),
        schema = config.property("$PATH.schema").getString(),
    )

    companion object {
        const val PATH = "${ConfigPaths.repository}.psql"
    }
}
