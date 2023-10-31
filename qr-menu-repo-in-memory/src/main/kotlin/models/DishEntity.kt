package models

data class DishEntity(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val cost: Double? = null,
    val type: String? = null,
    val visibility: String? = null,
    val ownerId: String? = null,
    val lock: String? = null
) {
    constructor(model: QrMenuDish) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        cost = model.cost.takeIf { it.isNaN() },
        type = model.type.takeIf { it != EQrMenuDishType.NONE }?.name,
        visibility = model.visibility.takeIf { it != EQrMenuVisibility.NONE }?.name,
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = QrMenuDish(
        id = id?.let { QrMenuDishId(it) } ?: QrMenuDishId.NONE,
        name = name ?: "",
        description = description ?: "",
        cost = cost ?: 0.0,
        type = type?.let { EQrMenuDishType.valueOf(it) } ?: EQrMenuDishType.NONE,
        visibility = visibility?.let { EQrMenuVisibility.valueOf(it) } ?: EQrMenuVisibility.NONE,
        ownerId = ownerId?.let { QrMenuUserId(it) }?: QrMenuUserId.NONE,
        lock = lock?.let { QrMenuDishLock(it) } ?: QrMenuDishLock.NONE,
    )
}

