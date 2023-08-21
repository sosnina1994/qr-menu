import models.*
import excepion.UnknownRequestClass
import ru.sosninanv.api.v1.models.*
import stubs.EQrMenuStubs

fun QrMenuContext.fromTransport(request: IRequest) = when (request) {
    is DishCreateRequest -> fromTransport(request)
    is DishReadRequest -> fromTransport(request)
    is DishUpdateRequest -> fromTransport(request)
    is DishDeleteRequest -> fromTransport(request)
    is DishSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)

}

fun QrMenuContext.fromTransport(request: DishCreateRequest)  {
    command = EQrMenuCommand.CREATE
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    dishRequest = request.dish?.toInternal() ?: QrMenuDish()
}

fun QrMenuContext.fromTransport(request: DishReadRequest) {
    command = EQrMenuCommand.READ
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    dishRequest = request.dish?.id.toDishWithId()
}


fun QrMenuContext.fromTransport(request: DishUpdateRequest) {
    command = EQrMenuCommand.UPDATE
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    dishRequest = request.dish?.toInternal() ?: QrMenuDish()
}

fun QrMenuContext.fromTransport(request: DishDeleteRequest) {
    command = EQrMenuCommand.DELETE
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    dishRequest = request.dish?.dish.toDishWithId()
}

fun QrMenuContext.fromTransport(request: DishSearchRequest) {
    command = EQrMenuCommand.SEARCH
    requestId = request.requestId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
    dishFilterRequest = request.dishFilter.toInternal()
}


private fun String?.toDishId() = this?.let { QrMenuDishId(it) } ?: QrMenuDishId.NONE

private fun String?.toDishWithId() = QrMenuDish(id = this.toDishId())

private fun IRequest?.requestId() = this?.requestId?.let { QrMenuRequestId(it) } ?: QrMenuRequestId.NONE

private fun DishDebug?.transportToWorkMode(): EQrMenuWorkMode = when (this?.mode) {
    EDishRequestDebugMode.TEST -> EQrMenuWorkMode.TEST
    EDishRequestDebugMode.STUB -> EQrMenuWorkMode.STUB
    EDishRequestDebugMode.PROD -> EQrMenuWorkMode.PROD
    else -> EQrMenuWorkMode.PROD
}

private fun DishDebug?.transportToStubCase(): EQrMenuStubs = when (this?.stub) {
    EDishRequestDebugStubs.SUCCESS -> EQrMenuStubs.SUCCESS
    EDishRequestDebugStubs.NOT_FOUND -> EQrMenuStubs.NOT_FOUND
    EDishRequestDebugStubs.BAD_ID -> EQrMenuStubs.BAD_ID
    EDishRequestDebugStubs.BAD_NAME -> EQrMenuStubs.BAD_NAME
    EDishRequestDebugStubs.BAD_DESCRIPTION -> EQrMenuStubs.BAD_DESCRIPTION
    EDishRequestDebugStubs.BAD_VISIBILITY -> EQrMenuStubs.BAD_VISIBILITY
    EDishRequestDebugStubs.CANNOT_DELETE -> EQrMenuStubs.CANNOT_DELETE
    EDishRequestDebugStubs.BAD_SEARCH_STRING -> EQrMenuStubs.BAD_SEARCH_STRING
    null -> EQrMenuStubs.NONE
}

private fun EDishType?.fromTransport(): EQrMenuDishType = when (this) {
    EDishType.DESSERT -> EQrMenuDishType.DESSERT
    EDishType.MAIN -> EQrMenuDishType.MAIN
    EDishType.APPETIZER -> EQrMenuDishType.APPETIZER
    null -> EQrMenuDishType.NONE

}

private fun DishCreateObject.toInternal(): QrMenuDish = QrMenuDish(
    name = this.name ?: "",
    description = this.description ?: "",
    type = this.dishType.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun DishUpdateObject.toInternal(): QrMenuDish = QrMenuDish(
    id = this.id.toDishId(),
    name = this.name ?: "",
    description = this.description ?: "",
    type = this.dishType.fromTransport(),
    visibility = this.visibility.fromTransport(),
)

private fun DishSearchFilter?.toInternal(): QrMenuDishFilter = QrMenuDishFilter(
    searchString = this?.searchString ?: ""
)

private fun EDishVisibility?.fromTransport(): EQrMenuVisibility = when (this) {
    EDishVisibility.PUBLIC -> EQrMenuVisibility.PUBLIC
    EDishVisibility.OWNER_ONLY -> EQrMenuVisibility.OWNER
    null -> EQrMenuVisibility.NONE
}




