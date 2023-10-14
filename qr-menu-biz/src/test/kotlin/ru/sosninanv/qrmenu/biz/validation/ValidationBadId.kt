package ru.sosninanv.qrmenu.biz.validation

import QrMenuContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {

    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = QrMenuDishId("123-234-abc-ABC"),
            name = "abc",
            description = "abc",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EQrMenuState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = QrMenuDishId(" \n\t 123-234-abc-ABC \n\t "),
            name = "abc",
            description = "abc",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EQrMenuState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = QrMenuDishId(""),
            name = "abc",
            description = "abc",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EQrMenuState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
    val ctx = QrMenuContext(
        command = command,
        state = EQrMenuState.NONE,
        workMode = EQrMenuWorkMode.TEST,
        dishRequest = QrMenuDish(
            id = QrMenuDishId("!@#\$%^&*(),.{}"),
            name = "abc",
            description = "abc",
            type = EQrMenuDishType.DESSERT,
            visibility = EQrMenuVisibility.PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EQrMenuState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
