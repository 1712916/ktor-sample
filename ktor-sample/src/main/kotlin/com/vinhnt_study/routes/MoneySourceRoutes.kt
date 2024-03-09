package com.vinhnt_study.routes

import com.vinhnt_study.models.MoneySource
import com.vinhnt_study.models.ResponseData
import com.vinhnt_study.services.MoneySourceService
import com.vinhnt_study.services.MoneySourceServiceImpl
import com.vinhnt_study.utils.getAccountId
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.moneySourceRoutes() {
    val moneySourceService: MoneySourceService = MoneySourceServiceImpl()
    authenticate("auth-jwt") {
        //route for money source
        get("api/money-sources") {
            //get all money sources by account id
            //get account id
            val principal = call.principal<JWTPrincipal>()
            val accountId = principal?.payload?.getClaim("account_id")?.asString() ?: ""

              call.respond(ResponseData.success(moneySourceService.findAll(accountId)))
        }

        //find by id
        get("api/money-sources/{id}") {

            //get account id
            val principal = call.principal<JWTPrincipal>()
            val accountId = principal?.payload?.getClaim("account_id")?.asString() ?: ""

            //get id parameter
            val id = call.parameters["id"] ?: ""
            call.respond(ResponseData.success(moneySourceService.findById(id, accountId)))
        }

        //create new money source
        post("api/money-sources") {
            //create new money source

            //get account id
            val principal = call.principal<JWTPrincipal>()
            val accountId = principal?.payload?.getClaim("account_id")?.asString() ?: ""

            //get money source name
            val moneySourceRequest = call.receive<MoneySourceRequest>()
            println("money source name: ${moneySourceRequest.name}")
            call.respond(ResponseData.success(moneySourceService.add(moneySourceRequest.name, accountId)))
        }

        //update money source
        put("api/money-sources") {
            //update money source
            val moneySourceRequest = call.receive<MoneySource>()

            call.respond(ResponseData.success(moneySourceService.update(moneySourceRequest, getAccountId(call))))

        }

        //delete money source
        delete("api/money-sources/{id}") {
            //get account id
            val principal = call.principal<JWTPrincipal>()
            val accountId = principal?.payload?.getClaim("account_id")?.asString() ?: ""

            //get money source id
            val id = call.parameters["id"] ?: ""
            call.respond(ResponseData.success(moneySourceService.delete(id, accountId)))
        }

    }

}

@Serializable
data class MoneySourceRequest (
    val name: String
)