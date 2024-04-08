package com.vinhnt_study.routes

import com.vinhnt_study.models.Money
import com.vinhnt_study.models.MoneyRequest
import com.vinhnt_study.models.ResponseData
import com.vinhnt_study.services.*
import com.vinhnt_study.utils.getAccountId
import com.vinhnt_study.utils.parseRequest
import com.vinhnt_study.utils.toDate
import com.vinhnt_study.utils.toUUID
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.expenseRoutes() {
    //declare expense service
    val expenseService = ExpenseServiceImpl()

    authenticate("auth-jwt") {
        //get all expenses
        get("api/expenses") {
            //call the service to get all expenses
            val expenses = expenseService.findAll(getAccountId(call))
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
            val expense = expenseService.findById(id, getAccountId(call))
            //return the expense with a 200 OK status and json format
            call.respond(
                ResponseData.success(expense)
            )
        }

        //add new expense
        post("api/expenses") {
            //get the expense from the request
            val expense = call.parseRequest<Money>()

            //call the service to add the expense
            val newExpense = expenseService.add(expense, getAccountId(call))

            call.respond(
                ResponseData.created(newExpense)
            )
        }

        //update expense
        put("api/expenses") {
            //get the id from the request
            //get the expense from the request and c

            val expense = call.parseRequest<Money>()
            //call the service to update the expense
            val updatedExpense = expenseService.update(expense, getAccountId(call))
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
            val deletedExpense = expenseService.delete(id, getAccountId(call))
            //return the deleted expense with a 200 OK status and json format
            call.respond(
                ResponseData.success(deletedExpense)
            )
        }


        //search from date to date
        get("api/expenses/search") {
            //get the from date and to date from the request
            val fromDate = call.request.queryParameters["fromDate"] ?: ""
            val toDate = call.request.queryParameters["toDate"] ?: ""
            val categoryIds = call.request.queryParameters.getAll("categoryId")
            val sourceIds = call.request.queryParameters.getAll("sourceId")
            //call the service to search for expenses from date to date
            val expenses = expenseService.search(
                accountId = getAccountId(call),
                fromDate = fromDate, toDate = toDate,
                categoryIds = categoryIds, sourceIds = sourceIds,
            )
            //return the expenses with a 200 OK status and json format
            call.respond(
                ResponseData.success(expenses)
            )
        }

        //search from date to date
        get("api/expenses/date/{date}") {
            val date = call.parameters["date"] ?: ""

            println("route request date: $date")

            //call the service to search for expenses from date to date
            val expenses = expenseService.getExpenseListByDate(
                accountId = getAccountId(call),
                date = date
            )
            //return the expenses with a 200 OK status and json format
            call.respond(
                ResponseData.success(expenses)
            )
        }
        get("api/expenses/total/{date}") {
            val date = call.parameters["date"] ?: ""

            println("route request date: $date")

            //call the service to search for expenses from date to date
            val expenses = expenseService.getExpenseListByDate(
                accountId = getAccountId(call),
                date = date
            )
            //return the expenses with a 200 OK status and json format
            call.respond(
                ResponseData.success(expenses)
            )
        }
    }
}

fun Route.totalExpenseRoutes() {
    //declare expense service
    val expenseService : TotalExpenseService = TotalExpenseServiceImpl()

    authenticate("auth-jwt") {

        get("api/expenses/date-total/{date}") {
            val date = call.parameters["date"] ?: ""

            println("route request date: $date")

            //call the service to search for expenses from date to date
            val expenses = expenseService.getTotalExpenseByDate(
                accountId = getAccountId(call),
                date = date.toDate()
            )
            //return the expenses with a 200 OK status and json format
            call.respond(
                ResponseData.success(expenses)
            )
        }

        get("api/expenses/total") {
            val fromDate = call.request.queryParameters["fromDate"] ?: ""
            val toDate = call.request.queryParameters["toDate"] ?: ""

            //call the service to search for expenses from date to date
            val expenses = expenseService.getTotalExpenseByDates(
                accountId = getAccountId(call),
               from = fromDate.toDate(),
                to = toDate.toDate(),
            )
            //return the expenses with a 200 OK status and json format
            call.respond(
                ResponseData.success(expenses)
            )
        }

        get("api/expenses/total-list") {
            val fromDate = call.request.queryParameters["fromDate"] ?: ""
            val toDate = call.request.queryParameters["toDate"] ?: ""

            //call the service to search for expenses from date to date
            val expenses = expenseService.getListTotalExpenseByDates(
                accountId = getAccountId(call),
                from = fromDate.toDate(),
                to = toDate.toDate(),
            )
            //return the expenses with a 200 OK status and json format
            call.respond(
                ResponseData.success(expenses)
            )
        }
    }
}
fun Route.totalExpenseByCategoryRoutes() {
    //declare expense service
    val expenseService : TotalExpenseCategoryService = TotalExpenseCategoryServiceImpl()

    authenticate("auth-jwt") {

        //Get list total expense by category from date to date
        get("api/expenses/category/total") {
            val fromDate = call.request.queryParameters["fromDate"] ?: ""
            val toDate = call.request.queryParameters["toDate"] ?: ""

            //call the service to search for expenses from date to date
            val expenses = expenseService.getListTotalExpenseByCategory(
                accountId = getAccountId(call),
                from = fromDate.toDate(),
                to = toDate.toDate(),
            )
            //return the expenses with a 200 OK status and json format
            call.respond(
                ResponseData.success(expenses)
            )
        }
    }
}



