package ru.sosninanv.qrmenu.biz.workers

import QrMenuContext
import QrMenuDishStub
import models.EQrMenuDishType
import models.EQrMenuState
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker
import stubs.EQrMenuStubs

fun ICorAddExecDsl<QrMenuContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == EQrMenuStubs.SUCCESS && state == EQrMenuState.RUNNING }
    handle {
        state = EQrMenuState.FINISHING
        dishesResponse.addAll(QrMenuDishStub.prepareSearchList(dishFilterRequest.searchString, EQrMenuDishType.DESSERT))
    }
}
