package ru.sosninanv.qrmenu.biz.workers

import QrMenuContext
import helpers.fail
import models.EQrMenuState
import models.QrMenuError
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == EQrMenuState.RUNNING }
    handle {
        fail(
            QrMenuError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
