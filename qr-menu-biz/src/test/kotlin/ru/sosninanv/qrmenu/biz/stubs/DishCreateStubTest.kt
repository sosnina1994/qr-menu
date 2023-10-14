package ru.sosninanv.qrmenu.biz.stubs

import QrMenuContext
import QrMenuDishStub
import kotlinx.coroutines.test.runTest
import models.*
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import stubs.EQrMenuStubs
import kotlin.test.Test
import kotlin.test.assertEquals

class DishCreateStubTest {

    private val processor = QrMenuDishProcessor()

    val id = QrMenuDishId("1")
    val name = "Dish1"
    val description = "Author"
    val cost = 100.0
    val type = EQrMenuDishType.DESSERT
    val ownerId = QrMenuUserId("user-1")
    val visibility = EQrMenuVisibility.PUBLIC

    @Test
    fun create() = runTest {

        val ctx = QrMenuContext(
            command = EQrMenuCommand.CREATE,
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
        assertEquals(QrMenuDishStub.get().id, ctx.dishResponse.id)

        assertEquals(name, ctx.dishResponse.name)
        assertEquals(description, ctx.dishResponse.description)
        assertEquals(type, ctx.dishResponse.type)
        assertEquals(visibility, ctx.dishResponse.visibility)
    }

    @Test
    fun badName() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.CREATE,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.BAD_NAME,
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
        assertEquals(QrMenuDish(), ctx.dishResponse)
        assertEquals("name", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = QrMenuContext(
            command = EQrMenuCommand.CREATE,
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
            command = EQrMenuCommand.CREATE,
            state = EQrMenuState.NONE,
            workMode = EQrMenuWorkMode.STUB,
            stubCase = EQrMenuStubs.BAD_ID,
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
        assertEquals(QrMenuDish(), ctx.dishResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
