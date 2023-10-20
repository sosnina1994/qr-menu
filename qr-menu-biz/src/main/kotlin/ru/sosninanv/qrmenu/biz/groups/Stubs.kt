package ru.sosninanv.qrmenu.biz.groups

import QrMenuContext
import models.EQrMenuState
import models.EQrMenuWorkMode
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.chain

fun ICorAddExecDsl<QrMenuContext>.stubs(title: String, block: ICorAddExecDsl<QrMenuContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == EQrMenuWorkMode.STUB && state == EQrMenuState.RUNNING }
}
