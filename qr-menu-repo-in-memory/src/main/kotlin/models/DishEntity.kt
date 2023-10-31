package models

data class DishEntity(
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val cost: Double? = null,
    val type: String? = null,
    val visibility: String? = null,
    val lock: String? = null
) {
    constructor(model: QrMenuDish) : this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        name = model.name.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        cost = model.cost.takeIf { it.isNaN() },
        type = model.type.takeIf { it != EQrMenuDishType.NONE }?.name,
        visibility = model.visibility.takeIf { it != EQrMenuVisibility.NONE }?.name,
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )
}

