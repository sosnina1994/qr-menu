import models.*

object QrMenuDishStubObj {

    val DISH: QrMenuDish
        get() = QrMenuDish(
            id = QrMenuDishId("1"),
            name = "Dish1",
            description = "Author",
            cost = 100.0,
            type = EQrMenuDishType.DESSERT,
            ownerId = QrMenuUserId("user-1"),
            visibility = EQrMenuVisibility.PUBLIC,
            permissions = mutableSetOf(
                EQrMenuPermissionClient.READ_ALL,
                EQrMenuPermissionClient.UPDATE,
                EQrMenuPermissionClient.DELETE,
                EQrMenuPermissionClient.MAKE_VISIBLE_PUBLIC,
                EQrMenuPermissionClient.MAKE_VISIBLE_OWNER,
            )
        )
    val COPY_DISH = DISH.copy(type = EQrMenuDishType.DESSERT)
}