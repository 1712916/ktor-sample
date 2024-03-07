package com.vinhnt_study

import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

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
}
