package ru.sosninanv.qrmenu.auth

import models.EQrMenuVisibility
import models.QrMenuDish
import models.QrMenuDishId
import permissions.QrMenuPrincipalModel
import permissions.QrMenuPrincipalRelations

fun QrMenuDish.resolveRelationsTo(principal: QrMenuPrincipalModel): Set<QrMenuPrincipalRelations> = setOfNotNull(
    QrMenuPrincipalRelations.NONE,
    QrMenuPrincipalRelations.NEW.takeIf { id == QrMenuDishId.NONE },
    QrMenuPrincipalRelations.ALL.takeIf { principal.id == ownerId },
    QrMenuPrincipalRelations.PUBLIC.takeIf { visibility == EQrMenuVisibility.PUBLIC },
)
