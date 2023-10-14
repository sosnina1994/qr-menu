package ru.sosninanv.qrmenu.biz.validation

import QrMenuContext
import helpers.errorValidation
import helpers.fail
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.validateNameNotEmpty(title: String) = worker {
    this.title = title
    on { dishValidating.name.isEmpty() }
    handle {
        fail(
            errorValidation(
            field = "title",
            violationCode = "empty",
            description = "field must not be empty"
        )
        )
    }
}
