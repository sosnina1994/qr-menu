import models.*

abstract class BaseInitDishes(val op: String): IInitObjects<QrMenuDish> {
    open val lockOld: QrMenuDishLock = QrMenuDishLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: QrMenuDishLock = QrMenuDishLock("20000000-0000-0000-0000-000000000009")

    fun createInitTestModel(
        suf: String,
        ownerId: QrMenuUserId = QrMenuUserId("owner-123"),
        type: EQrMenuDishType = EQrMenuDishType.MAIN,
        lock: QrMenuDishLock = lockOld,
    ) = QrMenuDish(
        id = QrMenuDishId("ad-repo-$op-$suf"),
        name = "$suf stub",
        description = "$suf stub description",
        ownerId = ownerId,
        type= type,
        visibility = EQrMenuVisibility.PUBLIC,
        lock = lock,
    )
}