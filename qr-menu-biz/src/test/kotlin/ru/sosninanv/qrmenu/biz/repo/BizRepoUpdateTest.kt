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

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = QrMenuUserId("321")
    private val command = EQrMenuCommand.UPDATE
    private val initDish = QrMenuDish(
        id = QrMenuDishId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        type = EQrMenuDishType.MAIN,
        visibility = EQrMenuVisibility.PUBLIC,
    )

    private val repo by lazy {DishRepositoryMock(
        invokeReadDish = { DbDishResponse(isSuccess = true, data = initDish,) },
        invokeUpdateDish = {
            DbDishResponse(
                isSuccess = true,
                data = QrMenuDish(
                    id = QrMenuDishId("123"),
                    name = "xyz",
                    description = "xyz",
                    type = EQrMenuDishType.MAIN,
                    visibility = EQrMenuVisibility.PUBLIC,
                )
            )
        }
    ) }
    private val settings by lazy { QrMenuCorSettings(repoTest = repo) }
    private val processor by lazy { QrMenuDishProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val dishToUpdate = QrMenuDish(
            id = QrMenuDishId("123"),
            name = "xyz",
            description = "xyz",
            type = EQrMenuDishType.MAIN,
            visibility = EQrMenuVisibility.PUBLIC,
            lock = QrMenuDishLock("123-234-abc-ABC"),
        )
        val ctx = QrMenuContext(
            command = command,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.TEST,
            dishRequest = dishToUpdate,
        )
        processor.exec(ctx)
        assertEquals(EQrMenuState.FINISHING, ctx.state)
        assertEquals(dishToUpdate.id, ctx.dishResponse.id)
        assertEquals(dishToUpdate.name, ctx.dishResponse.name)
        assertEquals(dishToUpdate.description, ctx.dishResponse.description)
        assertEquals(dishToUpdate.type, ctx.dishResponse.type)
        assertEquals(dishToUpdate.visibility, ctx.dishResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
