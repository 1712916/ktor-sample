package com.vinhnt_study

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.models.ResponseData
import com.vinhnt_study.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

//fun main() {
//   System.setProperty("io.ktor.development", "true")
//
//   embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
//       .start(wait = true)
//}

fun Application.module() {
    val env = System.getenv("env")
    val databaseEnvKey = "ktor.database${if (env.isNullOrEmpty()) "" else ".${env}"}"

    val url = environment.config.propertyOrNull("$databaseEnvKey.url")!!
        .getString()
    val user = environment.config.propertyOrNull("$databaseEnvKey.user")!!
        .getString()
    val password =
        environment.config.propertyOrNull("$databaseEnvKey.password")!!
            .getString()

    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowHeader(HttpHeaders.Authorization)
    }
    DatabaseSingleton.init(url, user, password)
    install(Authentication) {
        jwt("auth-jwt") {
            realm = ""
            verifier(
                JWT
                    .require(Algorithm.HMAC256("app-secret"))
                    .build()
            )

            validate { jwtCredential ->
                JWTPrincipal(jwtCredential.payload)
            }
        }
    }
    configureSerialization()
    configureRouting()
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(ResponseData.badRequest(cause.message ?: "Internal Server Error", null))
        }
    }
}