package com.vinhnt_study.plugins

import com.vinhnt_study.routes.expenseRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    routing {
        expenseRoutes()
    }
}
