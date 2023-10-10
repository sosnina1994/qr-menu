package ru.sosninanv.qrmenu.biz.workers

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker
import stubs.EQrMenuStubs

fun ICorAddExecDsl<QrMenuContext>.stubReadSuccess(title: String) = worker {
    this.title = title
    on { stubCase == EQrMenuStubs.SUCCESS && state == EQrMenuState.RUNNING }
    handle {
        state = EQrMenuState.FINISHING
        val stub = QrMenuDishStub.prepareResult {
            dishRequest.name.takeIf { it.isNotBlank() }?.also { this.name = it }
        }
        dishResponse = stub
    }
}
