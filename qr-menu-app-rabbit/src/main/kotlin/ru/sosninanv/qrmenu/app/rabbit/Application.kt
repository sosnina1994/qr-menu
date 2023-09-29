package ru.sosninanv.qrmenu.app.rabbit

import ru.sosninanv.qrmenu.app.rabbit.config.AppSettings

fun main() {
    val appSettings = AppSettings()
    appSettings.controller.start()

}