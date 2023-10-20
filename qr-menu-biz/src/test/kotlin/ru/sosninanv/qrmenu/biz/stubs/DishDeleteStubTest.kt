package ru.sosninanv.qrmenu.biz.stubs

import QrMenuContext
import QrMenuDishStub
import kotlinx.coroutines.test.runTest
import models.*
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import stubs.EQrMenuStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class DishDeleteStubTest {

    private val processor = QrMenuDishProcessor()
    private val id = QrMenuDishId("1")

    @Test
    fun delete() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.DELETE,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.SUCCESS,
            dishRequest = QrMenuDish(
                id = id
            )
        )
        processor.exec(ctx)
        with (QrMenuDishStub.get()) {
            assertEquals(id, ctx.dishResponse.id)
            assertEquals(name, ctx.dishResponse.name)
            assertEquals(description, ctx.dishResponse.description)
            assertEquals(type, ctx.dishResponse.type)
            assertEquals(visibility, ctx.dishResponse.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.DELETE,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.BAD_ID,
            dishRequest = QrMenuDish()
        )
        processor.exec(ctx)
        assertEquals(QrMenuDish(), ctx.dishResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.DELETE,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.DB_ERROR,
            dishRequest = QrMenuDish(
                id = id
            )
        )
        processor.exec(ctx)
        assertEquals(QrMenuDish(), ctx.dishResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.DELETE,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.BAD_ID,
            dishRequest = QrMenuDish(
                id = id
            )
        )
        processor.exec(ctx)
        assertEquals(QrMenuDish(), ctx.dishResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
