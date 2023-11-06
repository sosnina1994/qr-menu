package models

data class QrMenuDishFilter(
    var searchString: String = "",
    var ownerId: QrMenuUserId = QrMenuUserId.NONE,
    var dishType: EQrMenuDishType = EQrMenuDishType.NONE,
)
