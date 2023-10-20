package ru.sosninanv.qrmenu.biz.validation

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == EQrMenuState.RUNNING }
    handle {
        dishValidated = dishValidating
    }
}

fun ICorAddExecDsl<QrMenuContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == EQrMenuState.RUNNING }
    handle {
        dishFilterValidated = dishFilterValidating
    }
}
