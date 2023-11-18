package ru.sosninanv.qrmenu.app.plagins

import io.ktor.server.application.*
import ru.sosninanv.qrmenu.app.configs.AuthConfig

fun Application.initAppAuth(): AuthConfig = AuthConfig(
    secret = environment.config.propertyOrNull("menu.jwt.secret")?.getString() ?: "",
    issuer = environment.config.property("menu.jwt.issuer").getString(),
    audience = environment.config.property("menu.jwt.audience").getString(),
    realm = environment.config.property("menu.jwt.realm").getString(),
    clientId = environment.config.property("menu.jwt.clientId").getString(),
    certUrl = environment.config.propertyOrNull("menu.jwt.certUrl")?.getString(),
)
