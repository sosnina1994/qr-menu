package ru.sosninanv.qrmenu.biz.validation

import QrMenuContext
import helpers.errorValidation
import helpers.fail
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on { dishValidating.lock.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
