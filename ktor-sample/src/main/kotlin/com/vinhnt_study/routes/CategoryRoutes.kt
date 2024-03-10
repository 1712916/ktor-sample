package com.vinhnt_study.routes

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.categoryRoutes() {
    //with authorization aut-jwt
    authenticate("auth-jwt") {
        //route for category
        get("api/categories") {
            //get all categories
        }

        //find by id
        get("api/categories/{id}") {
            //get id parameter
        }

        //create new category
        post("api/categories") {
            //create new category
        }

        //update category
        put("api/categories") {
            //update category
        }
    }
}