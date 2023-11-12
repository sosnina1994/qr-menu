package ru.sosninanv.qrmenu.app.v1

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ru.sosninanv.qrmenu.app.QrMenuAppSettings

fun Route.v1Dish(appSettings: QrMenuAppSettings) {
    route("dish") {
        post("create") {
            call.createDish(appSettings)
        }
        post("read") {
            call.readDish(appSettings)
        }
        post("update") {
            call.updateDish(appSettings)
        }
        post("delete") {
            call.deleteDish(appSettings)
        }
        post("search") {
            call.searchDish(appSettings)
        }
    }
}
