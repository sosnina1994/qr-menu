import kotlinx.datetime.Instant
import models.*
import stubs.EQrMenuStubs

data class QrMenuContext(
    var command: EQrMenuCommand = EQrMenuCommand.NONE,
    var state: EQrMenuState = EQrMenuState.NONE,
    val errors: MutableList<QrMenuError> = mutableListOf(),

    var workMode: EQrMenuWorkMode = EQrMenuWorkMode.TEST,
    var stubCase: EQrMenuStubs = EQrMenuStubs.NONE,

    var requestId: QrMenuRequestId = QrMenuRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var dishRequest: QrMenuDish = QrMenuDish(),
    var dishFilterRequest: QrMenuDishFilter = QrMenuDishFilter(),
    var dishResponse: QrMenuDish = QrMenuDish(),
    var dishesResponse: MutableList<QrMenuDish> = mutableListOf(),


    )
