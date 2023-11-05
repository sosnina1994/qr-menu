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

class BizRepoReadTest {

    private val userId = QrMenuUserId("321")
    private val command = EQrMenuCommand.READ
    private val initAd = QrMenuDish(
        id = QrMenuDishId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        type = EQrMenuDishType.MAIN,
        visibility = EQrMenuVisibility.PUBLIC,
    )
    private val repo by lazy {
        DishRepositoryMock(
            invokeReadDish = {
                DbDishResponse(
                    isSuccess = true,
                    data = initAd,
                )
            }
        )
    }

    private val settings by lazy { QrMenuCorSettings(repoTest = repo) }
    private val processor by lazy { QrMenuDishProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = QrMenuContext(
            command = command,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.TEST,
            dishRequest = QrMenuDish(
                id = QrMenuDishId("123"),
            ),
        )


        processor.exec(ctx)
        assertEquals(EQrMenuState.FINISHING, ctx.state)
        assertEquals(initAd.id, ctx.dishResponse.id)
        assertEquals(initAd.name, ctx.dishResponse.name)
        assertEquals(initAd.description, ctx.dishResponse.description)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(command)
}
