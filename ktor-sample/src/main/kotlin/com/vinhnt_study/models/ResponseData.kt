package com.vinhnt_study.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer


//create a json response data class include data field is a Generic type, message and status field are String type
@Serializable
data class ResponseData<T>(
    val data: T, val message: String, val status: Int
) {
    //create a companion object to create a default success response
    companion object {
        fun <T> success(data: T): ResponseData<T> {
            return ResponseData(data, "Success", 200)
        }

        fun <T> created(data: T): ResponseData<T> {
            return ResponseData(data, "Created", 201)
        }

        fun <T> badRequest(message: String, data: T): ResponseData<T> {
            return ResponseData(data, message, 400)
        }
    }
}

//create error response when can not parse the request
