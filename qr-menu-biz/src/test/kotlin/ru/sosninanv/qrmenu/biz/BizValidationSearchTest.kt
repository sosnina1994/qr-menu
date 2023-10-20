package ru.sosninanv.qrmenu.biz

import QrMenuContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.EQrMenuCommand
import models.EQrMenuState
import models.EQrMenuWorkMode
import models.QrMenuDishFilter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = EQrMenuCommand.SEARCH
    private val processor by lazy { QrMenuDishProcessor() }

    @Test
    fun correctEmpty() = runTest {
        val ctx = QrMenuContext(
            command = command,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.TEST,
            dishFilterRequest = QrMenuDishFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(EQrMenuState.FAILING, ctx.state)
    }
}
