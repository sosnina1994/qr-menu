package ru.sosninanv.qrmenu.auth

import models.EQrMenuCommand
import permissions.QrMenuPrincipalRelations
import permissions.QrMenuUserPermissions

fun checkPermitted(
    command: EQrMenuCommand,
    relations: Iterable<QrMenuPrincipalRelations>,
    permissions: Iterable<QrMenuUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: EQrMenuCommand,
    val permission: QrMenuUserPermissions,
    val relation: QrMenuPrincipalRelations
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = EQrMenuCommand.CREATE,
        permission = QrMenuUserPermissions.CREATE,
        relation = QrMenuPrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command = EQrMenuCommand.READ,
        permission = QrMenuUserPermissions.READ_ALL,
        relation = QrMenuPrincipalRelations.ALL,
    ) to true,

    AccessTableConditions(
        command = EQrMenuCommand.READ,
        permission = QrMenuUserPermissions.READ_PUBLIC,
        relation = QrMenuPrincipalRelations.PUBLIC,
    ) to true,

    // Update
    AccessTableConditions(
        command = EQrMenuCommand.UPDATE,
        permission = QrMenuUserPermissions.UPDATE,
        relation = QrMenuPrincipalRelations.ALL,
    ) to true,

    // Delete
    AccessTableConditions(
        command = EQrMenuCommand.DELETE,
        permission = QrMenuUserPermissions.DELETE,
        relation = QrMenuPrincipalRelations.ALL,
    ) to true,
)
