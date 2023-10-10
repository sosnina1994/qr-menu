package ru.sosninanv.qrmenu.biz

import QrMenuContext
import QrMenuDishStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import stubs.EQrMenuStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class DishUpdateStubTest {

    private val processor = QrMenuDishProcessor()

    val id = QrMenuDishId("1")
    val name = "Dish1"
    val description = "Author"
    val cost = 100.0
    val type = EQrMenuDishType.DESSERT
    val ownerId = QrMenuUserId("user-1")
    val visibility = EQrMenuVisibility.PUBLIC

    @Test
    fun update() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.UPDATE,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.SUCCESS,
            dishRequest = QrMenuDish(
                id = id,
                name = name,
                description = description,
                cost = cost,
                type = type,
                ownerId = ownerId,
                visibility = visibility
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
            command = EQrMenuCommand.UPDATE,
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
            command = EQrMenuCommand.UPDATE,
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
            command = EQrMenuCommand.UPDATE,
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
