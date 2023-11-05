package ru.sosninanv.qrmenu.biz.repo

import DishRepositoryMock
import QrMenuContext
import QrMenuCorSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import repo.DbDishResponse
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {


    private val userId = QrMenuUserId("321")
    private val command = EQrMenuCommand.DELETE

    private val initDish = QrMenuDish(
        id = QrMenuDishId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        type = EQrMenuDishType.MAIN,
        visibility = EQrMenuVisibility.PUBLIC,
        lock = QrMenuDishLock("123-234-abc-ABC"),
    )

    private val repo by lazy {
        DishRepositoryMock(
            invokeReadDish = { DbDishResponse(isSuccess = true, data = initDish) },
            invokeDeleteDish = {
                if (it.id == initDish.id) DbDishResponse(isSuccess = true, data = initDish)
                else DbDishResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy { QrMenuCorSettings(repoTest = repo) }
    private val processor by lazy { QrMenuDishProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val dishToDelete = QrMenuDish(
            id = QrMenuDishId("123"),
            lock = QrMenuDishLock("123-234-abc-ABC"),
        )
        val ctx = QrMenuContext(
            command = command,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.TEST,
            dishRequest = dishToDelete,
        )
        processor.exec(ctx)
        assertEquals(EQrMenuState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initDish.id, ctx.dishResponse.id)
        assertEquals(initDish.name, ctx.dishResponse.name)
        assertEquals(initDish.description, ctx.dishResponse.description)
        assertEquals(initDish.type, ctx.dishResponse.type)
        assertEquals(initDish.visibility, ctx.dishResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
