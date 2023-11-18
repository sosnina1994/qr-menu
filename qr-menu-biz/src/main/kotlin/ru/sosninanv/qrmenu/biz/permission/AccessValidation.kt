package ru.sosninanv.qrmenu.biz.permission

import QrMenuContext
import helpers.fail
import models.EQrMenuState
import models.QrMenuError
import ru.sosninanv.qrmenu.auth.checkPermitted
import ru.sosninanv.qrmenu.auth.resolveRelationsTo
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.chain
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == EQrMenuState.RUNNING }
    worker("Вычисление отношения объявления к принципалу") {
        dishRepoRead.principalRelations = dishRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к объявлению") {
        permitted = checkPermitted(command, dishRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(QrMenuError(message = "User is not allowed to perform this operation"))
        }
    }
}
