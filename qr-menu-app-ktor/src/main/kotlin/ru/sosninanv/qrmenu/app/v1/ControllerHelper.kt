package ru.sosninanv.qrmenu.app.v1

import QrMenuContext
import fromTransport
import helpers.asQrMenuError
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.datetime.Clock
import models.EQrMenuState
import ru.sosninanv.api.v1.models.IRequest
import ru.sosninanv.api.v1.models.IResponse
import ru.sosninanv.qrmenu.app.QrMenuAppSettings
import toTransport
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.process(
    appSettings: QrMenuAppSettings,
    clazz: KClass<*>,
    logId: String,
) = appSettings.controllerHelper(
    { fromTransport(receive<Q>()) },
    { respond(toTransport()) },
    clazz,
    logId,
)

suspend inline fun <T> QrMenuAppSettings.controllerHelper(
    crossinline getRequest: suspend QrMenuContext.() -> Unit,
    crossinline toResponse: suspend QrMenuContext.() -> T,
    clazz: KClass<*>,
    logId: String,
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = QrMenuContext(
        timeStart = Clock.System.now(),
    )
    return try {
        logger.doWithLogging(logId) {
            ctx.getRequest()
            processor.exec(ctx)
            logger.info(
                msg = "Request $logId processed for ${clazz.simpleName}",
                marker = "BIZ",
                //data = ctx.toLog(logId)
            )
            ctx.toResponse()
        }
    } catch (e: Throwable) {
        logger.doWithLogging("$logId-failure") {
            ctx.state = EQrMenuState.FAILING
            ctx.errors.add(e.asQrMenuError())
            processor.exec(ctx)
            ctx.toResponse()
        }
    }
}