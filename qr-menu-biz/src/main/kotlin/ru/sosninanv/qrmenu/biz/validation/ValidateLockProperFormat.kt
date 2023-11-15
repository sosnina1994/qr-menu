package ru.sosninanv.qrmenu.biz.validation

import QrMenuContext
import helpers.errorValidation
import helpers.fail
import models.QrMenuDishLock
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.validateLockProperFormat(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { dishValidating.lock != QrMenuDishLock.NONE && !dishValidating.lock.asString().matches(regExp) }
    handle {
        val encodedId = dishValidating.lock.asString()
        fail(
            errorValidation(
                field = "lock",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
