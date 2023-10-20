package ru.sosninanv.qrmenu.biz.workers

import QrMenuContext
import QrMenuDishStub
import models.EQrMenuDishType
import models.EQrMenuState
import models.EQrMenuVisibility
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker
import stubs.EQrMenuStubs

fun ICorAddExecDsl<QrMenuContext>.stubUpdateSuccess(title: String) = worker {
    this.title = title
    on { stubCase == EQrMenuStubs.SUCCESS && state == EQrMenuState.RUNNING }
    handle {
        state = EQrMenuState.FINISHING
        val stub = QrMenuDishStub.prepareResult {
            dishRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
            dishRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            dishRequest.type.takeIf { it != EQrMenuDishType.NONE }?.also { this.type = it }
            dishRequest.visibility.takeIf { it != EQrMenuVisibility.NONE }?.also { this.visibility = it }
        }
        dishResponse = stub
    }
}
