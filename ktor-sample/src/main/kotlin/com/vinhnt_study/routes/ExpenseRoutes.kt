package com.vinhnt_study.routes

import com.vinhnt_study.data.models.Money
import com.vinhnt_study.data.models.MoneyRequest
import com.vinhnt_study.data.models.MoneyType
import com.vinhnt_study.data.models.ResponseData
import com.vinhnt_study.services.ExpenseService
import com.vinhnt_study.services.ExpenseServiceImpl
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.expenseRoutes() {
    //declare expense service
    val expenseService = ExpenseServiceImpl()

    //get all expenses
    get("api/expenses") {
        //call the service to get all expenses
        val expenses = expenseService.findAll()
        //return the expenses with a 200 OK status and json format
        call.respond(
            ResponseData.success(expenses)
        )
    }

    //get by id
    get("api/expenses/{id}") {
        //get the id from the request
        val id = call.parameters["id"] ?: ""
        //call the service to get the expense by id
        val expense = expenseService.findById(id)
        //return the expense with a 200 OK status and json format
        call.respond(
            ResponseData.success(expense)
        )
    }

    //add new expense
    post("api/expenses") {
        //get the expense from the request

        val expense = call.receive<MoneyRequest>()
        //call the service to add the expense
        val newExpense = expenseService.add(expense)

         call.respond(
            ResponseData.created(newExpense)
        )
    }

    //update expense
    put("api/expenses") {
        //get the id from the request
        //get the expense from the request
        val expense = call.receive<Money>()
        //call the service to update the expense
        val updatedExpense = expenseService.update(expense)
        //return the updated expense with a 200 OK status and json format
        call.respond(
            ResponseData.success(updatedExpense)
        )
    }

    //delete expense
    delete("api/expenses/{id}") {
        //get the id from the request
        val id = call.parameters["id"] ?: ""
        //call the service to delete the expense
        val deletedExpense = expenseService.delete(id)
        //return the deleted expense with a 200 OK status and json format
        call.respond(
            ResponseData.success(deletedExpense)
        )
    }
}