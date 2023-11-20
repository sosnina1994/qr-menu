package ru.sosninanv.qrmenu.app

import apiV1Mapper
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.event.Level
import ru.sosninanv.qrmenu.app.plagins.initAppSettings
import ru.sosninanv.qrmenu.app.v1.v1Dish
import ru.sosninanv.qrmenu.logging.MpLogWrapperLogback

fun main(args: Array<String>): Unit = EngineMain.main(args)

private val clazz = Application::moduleJvm::class.qualifiedName ?: "Application"
@Suppress("unused")
fun Application.moduleJvm(appSettings: QrMenuAppSettings = initAppSettings()) {

    install(CallLogging) {
        level = Level.INFO
        val lgr = appSettings
            .corSettings
            .loggerProvider
            .logger(clazz) as? MpLogWrapperLogback
        lgr?.logger?.also { logger = it }
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(ContentNegotiation) {
        jackson {
            setConfig(apiV1Mapper.serializationConfig)
            setConfig(apiV1Mapper.deserializationConfig)
        }
    }

    routing {
        get("/") {
            call.respondText("OK")
        }
        route("v1") {
            v1Dish(appSettings)
        }
    }
}