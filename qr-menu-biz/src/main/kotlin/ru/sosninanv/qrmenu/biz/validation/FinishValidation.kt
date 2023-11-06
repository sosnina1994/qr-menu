package ru.sosninanv.qrmenu.biz.validation

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.finishDishValidation(title: String) = worker {
    this.title = title
    on { state == EQrMenuState.RUNNING }
    handle {
        dishValidated = dishValidating
    }
}

fun ICorAddExecDsl<QrMenuContext>.finishDishFilterValidation(title: String) = worker {
    this.title = title
    on { state == EQrMenuState.RUNNING }
    handle {
        dishFilterValidated = dishFilterValidating
    }
}
