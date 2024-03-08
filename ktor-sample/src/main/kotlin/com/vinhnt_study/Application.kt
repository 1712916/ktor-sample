package com.vinhnt_study

import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun main() {
    System.setProperty("io.ktor.development", "true")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseSingleton.init()
    configureSecurity()
    configureSerialization()
    configureRouting()
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "Internal Server Error")
        }
    }
}
