package ru.sosninanv.qrmenu.biz.repo

import QrMenuContext
import models.EQrMenuState
import repo.DbDishIdRequest
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == EQrMenuState.RUNNING }
    handle {
        val request = DbDishIdRequest(dishValidated)
        val result = dishRepo.readDish(request)
        val resultDish = result.data
        if (result.isSuccess && resultDish != null) {
            dishRepoRead = resultDish
        } else {
            state = EQrMenuState.FAILING
            errors.addAll(result.errors)
        }
    }
}
