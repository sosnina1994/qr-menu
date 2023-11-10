package ru.sosninanv.qrmenu.app.plagins

import io.ktor.server.application.*
import ru.sosninanv.qrmenu.logging.MpLoggerProvider


fun Application.getLoggerProviderConf(): MpLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> MpLoggerProvider()
        else -> throw Exception("Logger $mode is not allowed. Additted values are kmp and logback")
    }