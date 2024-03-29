package com.vinhnt_study.routes

import com.vinhnt_study.models.ResponseData
import com.vinhnt_study.models.authentication.LoginRequest
import com.vinhnt_study.models.authentication.RegisterRequest
import com.vinhnt_study.services.AccountService
import com.vinhnt_study.services.AccountServiceImpl
import com.vinhnt_study.services.AuthenticationService
import com.vinhnt_study.services.AuthenticationServiceImpl
import com.vinhnt_study.utils.parseRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authenticationRoutes() {
    val authenticationService: AuthenticationService = AuthenticationServiceImpl()
    post("api/login") {
        val loginRequest = call.parseRequest<LoginRequest>()
        try {
            call.respond(ResponseData.success(authenticationService.loginByAccount(loginRequest.account, loginRequest.password)))

         } catch (e: IllegalArgumentException) {
            call.respond(HttpStatusCode.BadRequest, ResponseData.badRequest(e.message!!, null))
        }
    }

    //logout route
    delete("api/logout") {
        authenticationService.logout()
    }

    //register route
    post("api/register") {
        val registerRequest = call.parseRequest<RegisterRequest>()

        authenticationService.register(registerRequest.account, registerRequest.email, registerRequest.password).let {
            call.respond(ResponseData.created(it))
        }
    }

    //change password route
    put("api/change-password") {

    }

    //reset password route
    put("api/reset-password") {

    }

    authenticate("auth-jwt") {
        get("api/authorization") {
            val principal = call.principal<JWTPrincipal>()

            call.respond(ResponseData.success("Authorized"))
        }

    }


}
