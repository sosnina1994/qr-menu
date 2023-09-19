import models.*

object QrMenuDishStub {

    fun get() = QrMenuDish(
        id = QrMenuDishId("1"),
        name = "Dish1",
        description = "Author",
        cost = 100.0,
        type = EQrMenuDishType.DESSERT,
        ownerId = QrMenuUserId("user-1"),
        visibility = EQrMenuVisibility.PUBLIC,
        permissions = mutableSetOf(
            EQrMenuPermissionClient.READ,
            EQrMenuPermissionClient.UPDATE,
            EQrMenuPermissionClient.DELETE,
            EQrMenuPermissionClient.MAKE_VISIBLE_PUBLIC,
            EQrMenuPermissionClient.MAKE_VISIBLE_OWNER,
        )
    )

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
