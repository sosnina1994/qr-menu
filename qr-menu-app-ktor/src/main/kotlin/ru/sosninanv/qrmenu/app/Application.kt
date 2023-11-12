package ru.sosninanv.qrmenu.app

import apiV1Mapper
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.config.yaml.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.partialcontent.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.slf4j.event.Level
import ru.sosninanv.qrmenu.app.plagins.initAppSettings
import ru.sosninanv.qrmenu.app.v1.v1Dish
import ru.sosninanv.qrmenu.logging.MpLogWrapperLogback

fun main(args: Array<String>) {
    embeddedServer(Netty, environment = applicationEngineEnvironment {
        val conf = YamlConfigLoader().load("./application.yaml")
            ?: throw RuntimeException("Cannot read application.yaml")
        config = conf
        println("File read")

        module {
            moduleJvm()
        }
        connector {
            port = conf.tryGetString("ktor.deployment.port")?.toIntOrNull() ?: 8080
            host = conf.tryGetString("ktor.deployment.host") ?: "0.0.0.0"
        }
    }).apply {
        start(true)
    }
}

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
            v1Dish()
        }
    }
}