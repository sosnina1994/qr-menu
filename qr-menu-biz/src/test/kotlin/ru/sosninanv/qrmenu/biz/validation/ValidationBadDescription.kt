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
fun validationDescriptionCorrect(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
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
            lock = QrMenuDishLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EQrMenuState.FAILING, ctx.state)
    assertEquals("abc", ctx.dishValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionTrim(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
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
            lock = QrMenuDishLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EQrMenuState.FAILING, ctx.state)
    assertEquals("abc", ctx.dishValidated.name)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionEmpty(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = stub.id,
            name = "abc",
            description = "",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
            lock = QrMenuDishLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EQrMenuState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionSymbols(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = stub.id,
            name = "abc",
            description = "!@#$%^&*(),.{}",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
            lock = QrMenuDishLock("123-234-abc-ABC"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EQrMenuState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
