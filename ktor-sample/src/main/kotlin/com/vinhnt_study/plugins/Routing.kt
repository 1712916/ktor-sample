package com.vinhnt_study.plugins

import com.vinhnt_study.routes.authenticationRoutes
import com.vinhnt_study.routes.expenseRoutes
import com.vinhnt_study.routes.moneySourceRoutes
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
        authenticationRoutes()
        expenseRoutes()
        moneySourceRoutes()
    }
}
