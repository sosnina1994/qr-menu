package ru.sosninanv.qrmenu.biz.repo

import QrMenuContext
import models.EQrMenuState
import repo.DbDishFilterRequest
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == EQrMenuState.RUNNING }
    handle {
        val request = DbDishFilterRequest(
            titleFilter = dishFilterValidated.searchString,
            ownerId = dishFilterValidated.ownerId,
            dishType = dishFilterValidated.dishType,
        )
        val result = dishRepo.searchDish(request)
        val resultAds = result.data
        if (result.isSuccess && resultAds != null) {
            dishesRepoDone = resultAds.toMutableList()
        } else {
            state = EQrMenuState.FAILING
            errors.addAll(result.errors)
        }
    }
}
