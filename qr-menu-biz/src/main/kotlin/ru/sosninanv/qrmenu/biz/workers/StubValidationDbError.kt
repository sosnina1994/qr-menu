package ru.sosninanv.qrmenu.biz.workers

import QrMenuContext
import models.EQrMenuState
import models.QrMenuError
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker
import stubs.EQrMenuStubs

fun ICorAddExecDsl<QrMenuContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == EQrMenuStubs.DB_ERROR && state == EQrMenuState.RUNNING }
    handle {
        state = EQrMenuState.FAILING
        this.errors.add(
            QrMenuError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
