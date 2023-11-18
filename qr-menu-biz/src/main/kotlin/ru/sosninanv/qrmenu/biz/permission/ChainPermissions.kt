package ru.sosninanv.qrmenu.biz.permission

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.auth.resolveChainPermissions
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker


fun ICorAddExecDsl<QrMenuContext>.chainPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление прав доступа для групп пользователей"

    on { state == EQrMenuState.RUNNING }

    handle {
        permissionsChain.addAll(resolveChainPermissions(principal.groups))
        println("PRINCIPAL: $principal")
        println("PERMISSIONS: $permissionsChain")
    }
}
