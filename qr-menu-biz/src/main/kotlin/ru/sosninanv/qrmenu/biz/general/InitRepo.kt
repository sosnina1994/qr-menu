package ru.sosninanv.qrmenu.biz.general

import QrMenuContext
import helpers.errorAdministration
import helpers.fail
import models.EQrMenuWorkMode
import repo.IDishRepository
import ru.sosninanv.qrmenu.cor.ICorAddExecDsl
import ru.sosninanv.qrmenu.cor.handlers.worker

fun ICorAddExecDsl<QrMenuContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от режима работы        
    """.trimIndent()
    handle {
        dishRepo = when {
            workMode == EQrMenuWorkMode.TEST -> settings.repoTest
            workMode == EQrMenuWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != EQrMenuWorkMode.STUB && dishRepo == IDishRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
