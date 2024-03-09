package com.vinhnt_study.utils

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*

//create an utils to handle the parse request success and error
suspend inline fun <reified T : Any> ApplicationCall.parseRequest(): T {
    return try {
        this.receive<T>()
    } catch (e: Exception) {
        throw InvalidBodyException("Can not parse the request")
    }
}

fun getAccountId(call: ApplicationCall): String {
    val principal = call.principal<JWTPrincipal>()
    return principal?.payload?.getClaim("account_id")?.asString() ?: ""
}