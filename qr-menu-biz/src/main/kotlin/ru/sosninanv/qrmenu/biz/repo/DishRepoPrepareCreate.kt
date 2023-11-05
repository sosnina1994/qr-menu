package ru.sosninanv.qrmenu.biz.repo

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == EQrMenuState.RUNNING }
    handle {
        dishRepoRead = dishValidated.deepCopy()
        dishRepoPrepare = dishRepoRead

    }
}
