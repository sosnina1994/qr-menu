package ru.sosninanv.qrmenu.biz.repo

import DishRepositoryMock
import QrMenuContext
import QrMenuCorSettings
import kotlinx.coroutines.test.runTest
import models.*
import repo.DbDishResponse
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val userId = QrMenuUserId("321")
    private val command = EQrMenuCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = DishRepositoryMock(
        invokeCreateDish = {
            DbDishResponse(
                isSuccess = true,
                data = QrMenuDish(
                    id = QrMenuDishId(uuid),
                    name = it.dish.name,
                    description = it.dish.description,
                    ownerId = userId,
                    type = it.dish.type,
                    visibility = it.dish.visibility,
                )
            )
        }
    )
    private val settings = QrMenuCorSettings(repoTest = repo)
    private val processor = QrMenuDishProcessor(settings)

    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = QrMenuContext(
            command = command,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.TEST,
            dishRequest = QrMenuDish(
                name = "abc",
                description = "abc",
                type = EQrMenuDishType.MAIN,
                visibility = EQrMenuVisibility.PUBLIC,
            ),
        )
        processor.exec(ctx)
        assertEquals(EQrMenuState.FINISHING, ctx.state)
        assertNotEquals(QrMenuDishId.NONE, ctx.dishResponse.id)
        assertEquals("abc", ctx.dishResponse.name)
        assertEquals("abc", ctx.dishResponse.description)
    }
}
