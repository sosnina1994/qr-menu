package ru.sosninanv.qrmenu.biz.repo

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == EQrMenuState.RUNNING }
    handle {
        dishRepoPrepare = dishRepoRead.deepCopy().apply {
            this.name = dishValidated.name
            description = dishValidated.description
            cost = dishValidated.cost
            type = dishValidated.type
            visibility = dishValidated.visibility
        }
    }
}
