package helpers

import QrMenuContext
import models.EQrMenuCommand

fun QrMenuContext.isUpdatableCommand() =
    this.command in listOf(EQrMenuCommand.CREATE, EQrMenuCommand.UPDATE, EQrMenuCommand.DELETE)