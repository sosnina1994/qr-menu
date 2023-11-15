package ru.sosninanv.qrmenu.biz.general

import QrMenuContext
import models.EQrMenuState
import models.EQrMenuWorkMode
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != EQrMenuWorkMode.STUB }
    handle {
        dishResponse = dishRepoDone
        dishesResponse = dishesRepoDone
        state = when (val st = state) {
            EQrMenuState.RUNNING -> EQrMenuState.FINISHING
            else -> st
        }
    }
}
