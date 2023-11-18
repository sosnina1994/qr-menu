package ru.sosninanv.qrmenu.biz

import QrMenuContext
import models.QrMenuUserId
import permissions.QrMenuPrincipalModel
import permissions.QrMenuUserGroups

fun QrMenuContext.addTestPrincipal(userId: QrMenuUserId = QrMenuUserId("321")) {
    principal = QrMenuPrincipalModel(
        id = userId,
        groups = setOf(
            QrMenuUserGroups.ADMIN,
            QrMenuUserGroups.TEST,
        )
    )
}
