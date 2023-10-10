package ru.sosninanv.qrmenu.biz.workers

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == EQrMenuState.NONE }
    handle { state = EQrMenuState.RUNNING }
}