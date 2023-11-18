package ru.sosninanv.qrmenu.biz.repo

import DishRepositoryMock
import QrMenuContext
import QrMenuCorSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import repo.DbDishResponse
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import ru.sosninanv.qrmenu.biz.addTestPrincipal
import kotlin.test.assertEquals

private val initDish = QrMenuDish(
    id = QrMenuDishId("123"),
    name = "abc",
    description = "abc",
    type = EQrMenuDishType.MAIN,
    visibility = EQrMenuVisibility.PUBLIC,
)
private val repo = DishRepositoryMock(
        invokeReadDish = {
            if (it.id == initDish.id) {
                DbDishResponse(
                    isSuccess = true,
                    data = initDish,
                )
            } else DbDishResponse(
                isSuccess = false,
                data = null,
                errors = listOf(QrMenuError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy { QrMenuCorSettings(repoTest = repo) }
private val processor by lazy { QrMenuDishProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: EQrMenuCommand) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = QrMenuDishId("12345"),
            name = "xyz",
            description = "xyz",
            type = EQrMenuDishType.MAIN,
            visibility = EQrMenuVisibility.PUBLIC,
            lock = QrMenuDishLock("123-234-abc-ABC"),
        ),
    )

    ctx.addTestPrincipal()

    processor.exec(ctx)
    assertEquals(EQrMenuState.FAILING, ctx.state)
    assertEquals(QrMenuDish(), ctx.dishResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
