package models

data class QrMenuDish(
    var id: QrMenuDishId = QrMenuDishId.NONE,
    var name: String = "",
    var description: String = "",
    var cost: Double = 0.0,
    var type: EQrMenuDishType = EQrMenuDishType.NONE,
    var visibility: EQrMenuVisibility = EQrMenuVisibility.NONE,
    val permissions: MutableSet<EQrMenuPermissionClient> = mutableSetOf()
)