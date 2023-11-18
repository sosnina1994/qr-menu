package ru.sosninanv.qrmenu.auth

import permissions.QrMenuUserGroups
import permissions.QrMenuUserPermissions

fun resolveChainPermissions(
    groups: Iterable<QrMenuUserGroups>,
) = mutableSetOf<QrMenuUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    QrMenuUserGroups.USER to setOf(
        QrMenuUserPermissions.READ_PUBLIC,
        QrMenuUserPermissions.SEARCH_PUBLIC,
    ),
    QrMenuUserGroups.ADMIN to setOf(
        QrMenuUserPermissions.CREATE,
        QrMenuUserPermissions.READ_ALL,
        QrMenuUserPermissions.READ_PUBLIC,
        QrMenuUserPermissions.UPDATE,
        QrMenuUserPermissions.DELETE,
        QrMenuUserPermissions.SEARCH_ALL,
        QrMenuUserPermissions.SEARCH_PUBLIC,
    ),
    QrMenuUserGroups.TEST to setOf(
        QrMenuUserPermissions.CREATE,
        QrMenuUserPermissions.READ_ALL,
        QrMenuUserPermissions.READ_PUBLIC,
        QrMenuUserPermissions.UPDATE,
        QrMenuUserPermissions.DELETE,
        QrMenuUserPermissions.SEARCH_ALL,
        QrMenuUserPermissions.SEARCH_PUBLIC,
    ),
)

private val groupPermissionsDenys = mapOf(
    QrMenuUserGroups.USER to setOf(
        QrMenuUserPermissions.CREATE,
        QrMenuUserPermissions.DELETE,
        QrMenuUserPermissions.UPDATE,
        QrMenuUserPermissions.READ_ALL,
        QrMenuUserPermissions.SEARCH_ALL,
    ),
    QrMenuUserGroups.ADMIN to setOf(),
    QrMenuUserGroups.TEST to setOf(),
)
