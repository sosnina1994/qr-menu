package repo

import models.EQrMenuDishType
import models.QrMenuUserId

data class DbDishFilterRequest(
    val ownerId: QrMenuUserId = QrMenuUserId.NONE,
    val dishType: EQrMenuDishType = EQrMenuDishType.NONE
)
