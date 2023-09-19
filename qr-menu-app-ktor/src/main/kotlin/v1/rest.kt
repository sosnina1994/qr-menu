package v1

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.v1Dish() {
    route("dish") {
        post("create") {
            call.createDish()
        }
        post("read") {
            call.readDish()
        }
        post("update") {
            call.updateDish()
        }
        post("delete") {
            call.deleteDish()
        }
        post("search") {
            call.searchDish()
        }
    }
}
