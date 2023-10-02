package ru.sosninanv.qrmenu.biz

import QrMenuContext
import QrMenuDishStub
import models.EQrMenuCommand
import models.EQrMenuDishType
import models.EQrMenuWorkMode

class QrMenuDishProcessor {
    fun exec(ctx: QrMenuContext) {
        require(ctx.workMode == EQrMenuWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            EQrMenuCommand.SEARCH -> {
                ctx.dishesResponse.addAll(QrMenuDishStub.prepareSearchList("name", EQrMenuDishType.DESSERT))
            }
            else -> {
                ctx.dishResponse = QrMenuDishStub.get()
            }
        }
    }
}