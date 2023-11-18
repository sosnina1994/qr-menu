package ru.sosninanv.qrmenu.biz.permission

import QrMenuContext
import models.EQrMenuState
import ru.sosninanv.qrmenu.auth.resolveFrontPermissions
import ru.sosninanv.qrmenu.auth.resolveRelationsTo
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == EQrMenuState.RUNNING }

    handle {
        dishRepoDone.permissions.addAll(
            resolveFrontPermissions(
                permissionsChain,
                // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                dishRepoDone.resolveRelationsTo(principal)
            )
        )

        for (ad in dishesRepoDone) {
            ad.permissions.addAll(
                resolveFrontPermissions(
                    permissionsChain,
                    ad.resolveRelationsTo(principal)
                )
            )
        }
    }
}
