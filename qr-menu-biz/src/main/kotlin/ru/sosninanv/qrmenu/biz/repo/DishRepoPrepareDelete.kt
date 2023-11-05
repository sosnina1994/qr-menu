package ru.sosninanv.qrmenu.biz.repo

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == EQrMenuState.RUNNING }
    handle {
        dishRepoPrepare = dishValidated.deepCopy()
    }
}
