package ru.sosninanv.qrmenu.biz.repo

import QrMenuContext
import models.EQrMenuState
import repo.DbDishIdRequest
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление данных из БД по ID"
    on { state == EQrMenuState.RUNNING }
    handle {
        val request = DbDishIdRequest(dishRepoPrepare)
        val result = dishRepo.deleteDish(request)
        if (!result.isSuccess) {
            state = EQrMenuState.FAILING
            errors.addAll(result.errors)
        }
        dishRepoDone = dishRepoRead
    }
}
