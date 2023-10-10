package ru.sosninanv.qrmenu.biz.groups

import QrMenuContext
import models.EQrMenuCommand
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.chain
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.operation(
    title: String,
    command: EQrMenuCommand,
    block: ICorAddExecDsl<QrMenuContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == EQrMenuState.RUNNING }
}
