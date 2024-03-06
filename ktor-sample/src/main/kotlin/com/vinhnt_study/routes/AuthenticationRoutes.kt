package com.vinhnt_study.routes

import com.vinhnt_study.data.models.Money
import com.vinhnt_study.data.models.MoneyRequest
import com.vinhnt_study.data.models.MoneyType
import com.vinhnt_study.data.models.ResponseData
import com.vinhnt_study.services.ExpenseService
import com.vinhnt_study.services.ExpenseServiceImpl
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authenticationRoutes() {
    post("api/login") {

    }

    //logout route
    delete ("api/logout") {

    }

    //register route
    post("api/register") {

    }

    //change password route
    put("api/change-password") {

    }

    //reset password route
    put("api/reset-password") {

    }
}
