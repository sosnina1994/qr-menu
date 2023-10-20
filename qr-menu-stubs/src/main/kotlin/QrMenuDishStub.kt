import QrMenuDishStubObj.DISH
import models.*

object QrMenuDishStub {

    fun prepareResult(block: QrMenuDish.() -> Unit): QrMenuDish = get().apply(block)

    fun get(): QrMenuDish = DISH.copy()


    fun prepareSearchList(filter: String, type: EQrMenuDishType) = listOf(
        dishDemand("1", filter, type),
        dishDemand("2", filter, type),
        dishDemand("3", filter, type),
        dishDemand("4", filter, type),

    )

    private fun dishDemand(id: String, filter: String, type: EQrMenuDishType) =
        dish(get(), id = id, filter = filter, type = type)

    private fun dish(base: QrMenuDish, id: String, filter: String, type: EQrMenuDishType) = base.copy(
        id = QrMenuDishId(id),
        description = "desc $filter $id",
        type = type,
    )
}
