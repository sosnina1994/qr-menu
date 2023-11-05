package ru.sosninanv.qrmenu.biz.repo

import QrMenuContext
import models.EQrMenuState
import repo.DbDishRequest
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == EQrMenuState.RUNNING }
    handle {
        val request = DbDishRequest(dishRepoPrepare)
        val result = dishRepo.updateDish(request)
        val resultDish = result.data
        if (result.isSuccess && resultDish != null) {
            dishRepoDone = resultDish
        } else {
            state = EQrMenuState.FAILING
            errors.addAll(result.errors)
            dishRepoDone
        }
    }
}
