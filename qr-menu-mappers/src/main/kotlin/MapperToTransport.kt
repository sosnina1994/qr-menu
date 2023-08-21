import excepion.UnknownQrMenuCommand
import models.*
import ru.sosninanv.api.v1.models.*
import ru.sosninanv.api.v1.models.DishCreateResponse
import ru.sosninanv.api.v1.models.EResponseResult
import ru.sosninanv.api.v1.models.IResponse

fun QrMenuContext.toTransport(): IResponse = when (val cmd = command) {
    EQrMenuCommand.CREATE -> toTransportCreate()
    EQrMenuCommand.READ -> toTransportRead()
    EQrMenuCommand.UPDATE -> toTransportUpdate()
    EQrMenuCommand.DELETE -> toTransportDelete()
    EQrMenuCommand.SEARCH -> toTransportSearch()
    EQrMenuCommand.NONE -> throw UnknownQrMenuCommand(cmd)
}

fun QrMenuContext.toTransportCreate() = DishCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EQrMenuState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dish = dishResponse.toTransportDish()
)

fun QrMenuContext.toTransportRead() = DishReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EQrMenuState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dish = dishResponse.toTransportDish()
)

fun QrMenuContext.toTransportUpdate() = DishUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EQrMenuState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dish = dishResponse.toTransportDish()
)

fun QrMenuContext.toTransportDelete() = DishDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EQrMenuState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dish = dishResponse.toTransportDish()
)

fun QrMenuContext.toTransportSearch() = DishSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == EQrMenuState.RUNNING) EResponseResult.SUCCESS else EResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    dishes = dishesResponse.toTransportDish()
)

fun List<QrMenuDish>.toTransportDish(): List<DishResponseObject>? = this
    .map { it.toTransportDish() }
    .toList()
    .takeIf { it.isNotEmpty() }


private fun QrMenuDish.toTransportDish(): DishResponseObject = DishResponseObject(
    id = id.takeIf { it != QrMenuDishId.NONE }?.asString(),
    name = name.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    cost = cost.takeIf { !it.isNaN() },
    dishType = type.toTransportDish(),
    ownerId = ownerId.takeIf { it != QrMenuUserId.NONE }?.asString(),

    visibility = visibility.toTransportDish(),
    permissions = permissions.toTransportDish(),
)

private fun EQrMenuVisibility.toTransportDish(): EDishVisibility? = when (this) {
    EQrMenuVisibility.PUBLIC -> EDishVisibility.PUBLIC
    EQrMenuVisibility.OWNER -> EDishVisibility.OWNER_ONLY
    EQrMenuVisibility.NONE -> null
}

private fun EQrMenuDishType.toTransportDish(): EDishType? = when (this) {
    EQrMenuDishType.APPETIZER -> EDishType.APPETIZER
    EQrMenuDishType.MAIN -> EDishType.MAIN
    EQrMenuDishType.DESSERT -> EDishType.DESSERT
    EQrMenuDishType.NONE -> null
}


private fun Set<EQrMenuPermissionClient>.toTransportDish(): Set<DishPermissions>? = this
    .map { it.toTransportDish() }
    .toSet()
    .takeIf { it.isNotEmpty() }


private fun EQrMenuPermissionClient.toTransportDish() = when (this) {
    EQrMenuPermissionClient.READ -> DishPermissions.READ
    EQrMenuPermissionClient.UPDATE -> DishPermissions.UPDATE
    EQrMenuPermissionClient.MAKE_VISIBLE_OWNER -> DishPermissions.MAKE_VISIBLE_OWN
    EQrMenuPermissionClient.MAKE_VISIBLE_PUBLIC -> DishPermissions.MAKE_VISIBLE_PUBLIC
    EQrMenuPermissionClient.DELETE -> DishPermissions.DELETE
}


/**
 * Маппинг ошибок
 */
private fun List<QrMenuError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun QrMenuError.toTransport() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)