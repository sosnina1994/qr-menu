package ru.sosninanv.qrmenu.biz.workers

import QrMenuContext
import models.EQrMenuState
import models.QrMenuError
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker
import stubs.EQrMenuStubs

fun ICorAddExecDsl<QrMenuContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    on { stubCase == EQrMenuStubs.BAD_ID && state == EQrMenuState.RUNNING }
    handle {
        state = EQrMenuState.FAILING
        this.errors.add(
            QrMenuError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}
