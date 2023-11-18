import kotlinx.datetime.Instant
import models.*
import permissions.QrMenuPrincipalModel
import permissions.QrMenuUserPermissions
import repo.IDishRepository
import stubs.EQrMenuStubs

data class QrMenuContext(
    var command: EQrMenuCommand = EQrMenuCommand.NONE,
    var state: EQrMenuState = EQrMenuState.NONE,
    val errors: MutableList<QrMenuError> = mutableListOf(),
    var settings: QrMenuCorSettings = QrMenuCorSettings.NONE,

    var workMode: EQrMenuWorkMode = EQrMenuWorkMode.TEST,
    var stubCase: EQrMenuStubs = EQrMenuStubs.NONE,

    var dishRepo: IDishRepository = IDishRepository.NONE,
    var dishRepoRead: QrMenuDish = QrMenuDish(), // То, что прочитали из репозитория
    var dishRepoPrepare: QrMenuDish = QrMenuDish(), // То, что готовим для сохранения в БД
    var dishRepoDone: QrMenuDish = QrMenuDish(),  // Результат, полученный из БД
    var dishesRepoDone: MutableList<QrMenuDish> = mutableListOf(),

    var requestId: QrMenuRequestId = QrMenuRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var dishRequest: QrMenuDish = QrMenuDish(),
    var dishFilterRequest: QrMenuDishFilter = QrMenuDishFilter(),
    var dishResponse: QrMenuDish = QrMenuDish(),
    var dishesResponse: MutableList<QrMenuDish> = mutableListOf(),

    /** Валидируемые */
    var dishValidating: QrMenuDish = QrMenuDish(),
    var dishFilterValidating: QrMenuDishFilter = QrMenuDishFilter(),

    /** Готовый результат валидации */
    var dishValidated: QrMenuDish = QrMenuDish(),
    var dishFilterValidated: QrMenuDishFilter = QrMenuDishFilter(),

    /** Авторизация */
    var principal: QrMenuPrincipalModel = QrMenuPrincipalModel.NONE,
    var permissionsChain: MutableSet<QrMenuUserPermissions> = mutableSetOf(),
    var permitted: Boolean = false
)
