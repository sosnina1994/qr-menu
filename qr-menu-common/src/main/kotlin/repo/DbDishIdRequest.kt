package repo

import models.QrMenuDish
import models.QrMenuDishId
import models.QrMenuDishLock

data class DbDishIdRequest(
    val id: QrMenuDishId,
    val lock: QrMenuDishLock = QrMenuDishLock.NONE,
) {
    constructor(dish: QrMenuDish): this(dish.id, dish.lock)
}
