package ru.sosninanv.qrmenu.biz.validation

import QrMenuContext
import QrMenuDishStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = QrMenuDishStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameCorrect(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = stub.id,
            name = "abc",
            description = "abc",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EQrMenuState.FAILING, ctx.state)
    assertEquals("abc", ctx.dishValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameTrim(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = stub.id,
            name = " \n\t abc \t\n ",
            description = "abc",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EQrMenuState.FAILING, ctx.state)
    assertEquals("abc", ctx.dishValidated.name)
}


@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameEmpty(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = stub.id,
            name = "",
            description = "abc",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EQrMenuState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationNameSymbols(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = stub.id,
            name = "!@#$%^&*(),.{}",
            description = "abc",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EQrMenuState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("name", error?.field)
    assertContains(error?.message ?: "", "name")
}
