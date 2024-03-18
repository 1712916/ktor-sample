package com.vinhnt_study

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun main() {
    System.setProperty("io.ktor.development", "true")

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
    }
    DatabaseSingleton.init()
    install(Authentication) {
        jwt("auth-jwt") {
            realm = ""
            verifier(
                JWT
                    .require(Algorithm.HMAC256("app-secret"))
                    .build())

            validate {
                    jwtCredential ->
                JWTPrincipal(jwtCredential.payload)
            }
        }
    }
    configureSerialization()
    configureRouting()
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "Internal Server Error")
        }
    }

}