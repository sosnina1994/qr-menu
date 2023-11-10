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
fun validationLockCorrect(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
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
            lock = QrMenuDishLock("123-234-abc-ABC"),
        ),
    )

    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EQrMenuState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockTrim(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
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
            lock = QrMenuDishLock(" \n\t 123-234-abc-ABC \n\t "),
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(EQrMenuState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockEmpty(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
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
            lock = QrMenuDishLock(""),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EQrMenuState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationLockFormat(command: EQrMenuCommand, processor: QrMenuDishProcessor) = runTest {
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
            lock = QrMenuDishLock("!@#\$%^&*(),.{}"),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(EQrMenuState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "id")
}
