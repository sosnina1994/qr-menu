package ru.sosninanv.qrmenu.app

import QrMenuCorSettings
import ru.sosninanv.qrmenu.biz.QrMenuDishProcessor

data class QrMenuAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: QrMenuCorSettings,
    val processor: QrMenuDishProcessor = QrMenuDishProcessor(corSettings),
)
