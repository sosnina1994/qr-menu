package ru.sosninanv.qrmenu.biz.workers

import QrMenuContext
import models.EQrMenuState
import models.QrMenuError
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker
import stubs.EQrMenuStubs

fun ICorAddExecDsl<QrMenuContext>.stubValidationBadName(title: String) = worker {
    this.title = title
    on { stubCase == EQrMenuStubs.BAD_NAME && state == EQrMenuState.RUNNING }
    handle {
        state = EQrMenuState.FAILING
        this.errors.add(
            QrMenuError(group = "validation",
                code = "validation-title",
                field = "name",
                message = "Wrong title field")
        )
    }
}
