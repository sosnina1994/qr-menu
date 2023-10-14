package ru.sosninanv.qrmenu.biz.validation

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.chain

fun ICorAddExecDsl<QrMenuContext>.validation(block: ICorAddExecDsl<QrMenuContext>.() -> Unit) = chain {
    block()
    title = "Валидация"
    on { state == EQrMenuState.RUNNING }
}
