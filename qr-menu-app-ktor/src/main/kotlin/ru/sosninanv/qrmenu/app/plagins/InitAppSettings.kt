package ru.sosninanv.qrmenu.app.plagins

import DishRepoInMemory
import DishRepoStub
import QrMenuCorSettings
import io.ktor.server.application.*
import ru.sosninanv.qrmenu.app.QrMenuAppSettings
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor
import ru.sosninanv.qrmenu.repo.postgresql.RepoDishSQL


fun Application.initAppSettings(): QrMenuAppSettings {
    val corSettings = QrMenuCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = getDatabaseConf(DbType.TEST),
        repoProd = getDatabaseConf(DbType.PROD),
        repoStub = DishRepoStub(),
    )
    return QrMenuAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = QrMenuDishProcessor(corSettings),
    )
}
