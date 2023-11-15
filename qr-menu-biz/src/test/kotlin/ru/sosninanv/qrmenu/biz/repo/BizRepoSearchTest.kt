package ru.sosninanv.qrmenu.biz.repo

import DishRepositoryMock
import QrMenuContext
import QrMenuCorSettings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import repo.DbDishesResponse
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val userId = QrMenuUserId("321")
    private val command = EQrMenuCommand.SEARCH
    private val initDish = QrMenuDish(
        id = QrMenuDishId("123"),
        name = "abc",
        description = "abc",
        ownerId = userId,
        type = EQrMenuDishType.MAIN,
        visibility = EQrMenuVisibility.PUBLIC,
    )

    private val repo by lazy {
        DishRepositoryMock(
            invokeSearchDish = { DbDishesResponse(isSuccess = true, data = listOf(initDish)) }
        )
    }
    private val settings by lazy { QrMenuCorSettings(repoTest = repo) }
    private val processor by lazy { QrMenuDishProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = QrMenuContext(
            command = command,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.TEST,
            dishFilterRequest = QrMenuDishFilter(
                searchString = "ab",
                dishType = EQrMenuDishType.MAIN
            ),
        )
        processor.exec(ctx)
        assertEquals(EQrMenuState.FINISHING, ctx.state)
        assertEquals(1, ctx.dishesResponse.size)
    }
}
