package com.vinhnt_study.routes

import com.vinhnt_study.models.Category
import com.vinhnt_study.models.ResponseData
import com.vinhnt_study.services.CategoryCountService
import com.vinhnt_study.services.CategoryCountServiceImpl
import com.vinhnt_study.services.CategoryService
import com.vinhnt_study.services.CategoryServiceImpl
import com.vinhnt_study.utils.getAccountId
import com.vinhnt_study.utils.toDate
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes() {
    //category service
    val categoryService: CategoryService = CategoryServiceImpl()

    //with authorization aut-jwt
    authenticate("auth-jwt") {
        //route for category
        get("api/categories") {
            //get all categories
            call.respond(ResponseData.success(categoryService.findAll(getAccountId(call))))
        }

        //find by id
        get("api/categories/{id}") {
            //get id parameter
            val id = call.parameters["id"] ?: ""
            call.respond(ResponseData.success(categoryService.findById(id, getAccountId(call))))
        }

        //create new category
        post("api/categories") {
            //create new category
            val categoryRequest = call.receive<Category>()
            call.respond(ResponseData.success(categoryService.add(categoryRequest, getAccountId(call))))
        }

        //update category
        put("api/categories") {
            //update category
            val categoryRequest = call.receive<Category>()
            call.respond(ResponseData.success(categoryService.update(categoryRequest, getAccountId(call))))
        }

        //delete category
        delete("api/categories/{id}") {
            //get id parameter
            val id = call.parameters["id"] ?: ""
            call.respond(ResponseData.success(categoryService.delete(id, getAccountId(call))))
        }
    }
}

fun Route.categoryCountRoutes() {
    val categoryService: CategoryCountService = CategoryCountServiceImpl()

    authenticate("auth-jwt") {

        get("api/categories-count") {
            val fromDate = call.request.queryParameters["fromDate"] ?: ""
            val toDate = call.request.queryParameters["toDate"] ?: ""

            call.respond(ResponseData.success(categoryService.countAll(getAccountId(call), fromDate.toDate(), toDate.toDate())))
        }

        get("api/categories-count/{id}") {
         val id = call.parameters["id"] ?: ""
            call.respond(ResponseData.success(categoryService.countById(getAccountId(call), id)))
        }

        get("api/categories-count/by-ids") {

            val ids = call.request.queryParameters.getAll("id") ?: emptyList()

            call.respond(ResponseData.success(categoryService.countByIds(getAccountId(call), ids)))
        }
    }
}