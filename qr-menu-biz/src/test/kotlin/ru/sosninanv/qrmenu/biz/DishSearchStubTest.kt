package ru.sosninanv.qrmenu.biz

import QrMenuContext
import QrMenuDishStub
import kotlinx.coroutines.test.runTest
import models.*
import stubs.EQrMenuStubs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class DishSearchStubTest {

    private val processor = QrMenuDishProcessor()
    val filter = QrMenuDishFilter(searchString = "Dish1")

    @Test
    fun search() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.SEARCH,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.SUCCESS,
            dishFilterRequest = filter
        )
        processor.exec(ctx)
        assertTrue(ctx.dishesResponse.size > 1)
        val first = ctx.dishesResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.name.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (QrMenuDishStub.get()) {
            assertEquals(type, first.type)
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.SEARCH,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.BAD_ID,
            dishFilterRequest = filter
        )
        processor.exec(ctx)
        assertEquals(QrMenuDish(), ctx.dishResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.SEARCH,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.DB_ERROR,
            dishFilterRequest = filter
        )
        processor.exec(ctx)
        assertEquals(QrMenuDish(), ctx.dishResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.SEARCH,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.BAD_NAME,
            dishFilterRequest = filter
        )
        processor.exec(ctx)
        assertEquals(QrMenuDish(), ctx.dishResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
