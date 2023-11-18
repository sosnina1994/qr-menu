package ru.sosninanv.qrmenu.auth

import models.EQrMenuPermissionClient
import permissions.QrMenuPrincipalRelations
import permissions.QrMenuUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<QrMenuUserPermissions>,
    relations: Iterable<QrMenuPrincipalRelations>,
) = mutableSetOf<EQrMenuPermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    QrMenuUserPermissions.READ_ALL to mapOf(
        QrMenuPrincipalRelations.ALL to EQrMenuPermissionClient.READ_ALL
    ),

    QrMenuUserPermissions.READ_PUBLIC to mapOf(
        QrMenuPrincipalRelations.PUBLIC to EQrMenuPermissionClient.READ_PUBLIC
    ),

    // UPDATE
    QrMenuUserPermissions.UPDATE to mapOf(
        QrMenuPrincipalRelations.ALL to EQrMenuPermissionClient.UPDATE
    ),

    // DELETE
    QrMenuUserPermissions.DELETE to mapOf(
        QrMenuPrincipalRelations.ALL to EQrMenuPermissionClient.DELETE
    )
)
