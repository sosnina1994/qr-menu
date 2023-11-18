package models

import permissions.QrMenuPrincipalRelations

data class QrMenuDish(
    var id: QrMenuDishId = QrMenuDishId.NONE,
    var name: String = "",
    var description: String = "",
    var cost: Double = 0.0,
    var type: EQrMenuDishType = EQrMenuDishType.NONE,
    var ownerId: QrMenuUserId = QrMenuUserId.NONE,
    var visibility: EQrMenuVisibility = EQrMenuVisibility.NONE,
    var lock: QrMenuDishLock = QrMenuDishLock.NONE,
    var principalRelations: Set<QrMenuPrincipalRelations> = emptySet(),
    val permissions: MutableSet<EQrMenuPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): QrMenuDish = copy(
        permissions = permissions.toMutableSet()
    )
}