package ru.sosninanv.qrmenu.app.v1

import io.ktor.server.application.*
import ru.sosninanv.api.v1.models.*
import ru.sosninanv.qrmenu.app.QrMenuAppSettings
import kotlin.reflect.KClass


/** CREATE */

private val clCreate: KClass<*> = ApplicationCall::createDish::class
suspend fun ApplicationCall.createDish(appSettings: QrMenuAppSettings) =
    process<DishCreateRequest, DishCreateResponse>(appSettings, clCreate, "create")

/** READ */
private val clRead: KClass<*> = ApplicationCall::readDish::class
suspend fun ApplicationCall.readDish(appSettings: QrMenuAppSettings) =
    process<DishReadRequest, DishReadResponse>(appSettings, clRead, "read")

/** UPDATE */
private val clUpdate: KClass<*> = ApplicationCall::updateDish::class
suspend fun ApplicationCall.updateDish(appSettings: QrMenuAppSettings) =
    process<DishUpdateRequest, DishUpdateResponse>(appSettings, clUpdate, "update")

/** DELETE */
private val clDelete: KClass<*> = ApplicationCall::deleteDish::class
suspend fun ApplicationCall.deleteDish(appSettings: QrMenuAppSettings) =
    process<DishDeleteRequest, DishDeleteResponse>(appSettings, clDelete, "delete")

/** SEARCH */
private val clSearch: KClass<*> = ApplicationCall::searchDish::class
suspend fun ApplicationCall.searchDish(appSettings: QrMenuAppSettings) =
    process<DishSearchRequest, DishSearchResponse>(appSettings, clSearch, "search")
