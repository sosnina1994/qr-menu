package ru.sosninanv.qrmenu.app.v1

import QrMenuContext
import fromTransport
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import models.EQrMenuDishType
import ru.sosninanv.api.v1.models.*
import toTransportCreate
import toTransportDelete
import toTransportRead
import toTransportSearch
import toTransportUpdate

//FIXME: Добавить бизнес-логику
suspend fun ApplicationCall.createDish() {
    val request = receive<DishCreateRequest>()
    val context = QrMenuContext()
    context.fromTransport(request)
    context.dishResponse = QrMenuDishStub.get()
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readDish() {
    val request = receive<DishReadRequest>()
    val context = QrMenuContext()
    context.fromTransport(request)
    context.dishResponse = QrMenuDishStub.get()
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateDish() {
    val request = receive<DishUpdateRequest>()
    val context = QrMenuContext()
    context.fromTransport(request)
    context.dishResponse = QrMenuDishStub.get()
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteDish() {
    val request = receive<DishDeleteRequest>()
    val context = QrMenuContext()
    context.fromTransport(request)
    context.dishResponse = QrMenuDishStub.get()
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchDish() {
    val request = receive<DishSearchRequest>()
    val context = QrMenuContext()
    context.fromTransport(request)
    context.dishesResponse.addAll(QrMenuDishStub.prepareSearchList("name", EQrMenuDishType.DESSERT))
    respond(context.toTransportSearch())
}