package ru.sosninanv.qrmenu.app.base

import io.ktor.server.auth.jwt.*
import models.QrMenuUserId
import permissions.QrMenuPrincipalModel
import permissions.QrMenuUserGroups
import ru.sosninanv.qrmenu.app.configs.AuthConfig.Companion.F_NAME_CLAIM
import ru.sosninanv.qrmenu.app.configs.AuthConfig.Companion.GROUPS_CLAIM
import ru.sosninanv.qrmenu.app.configs.AuthConfig.Companion.ID_CLAIM
import ru.sosninanv.qrmenu.app.configs.AuthConfig.Companion.L_NAME_CLAIM
import ru.sosninanv.qrmenu.app.configs.AuthConfig.Companion.M_NAME_CLAIM

fun JWTPrincipal?.toModel() = this?.run {
    QrMenuPrincipalModel(
        id = payload.getClaim(ID_CLAIM).asString()?.let { QrMenuUserId(it) } ?: QrMenuUserId.NONE,
        fname = payload.getClaim(F_NAME_CLAIM).asString() ?: "",
        mname = payload.getClaim(M_NAME_CLAIM).asString() ?: "",
        lname = payload.getClaim(L_NAME_CLAIM).asString() ?: "",
        groups = payload
            .getClaim(GROUPS_CLAIM)
            ?.asList(String::class.java)
            ?.mapNotNull {
                when(it) {
                    "USER" -> QrMenuUserGroups.USER
                    "TEST" -> QrMenuUserGroups.TEST
                    "ADMIN" -> QrMenuUserGroups.ADMIN
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: QrMenuPrincipalModel.NONE
