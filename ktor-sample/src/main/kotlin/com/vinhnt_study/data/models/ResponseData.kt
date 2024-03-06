package com.vinhnt_study.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer


//create a json response data class include data field is a Generic type, message and status field are String type
@Serializable
data class ResponseData<T>(
    val data: T,
    val message: String,
    val status: String
) {
    //create a companion object to create a default success response
    companion object {
        fun <T> success(data: T): ResponseData<T> {
            return ResponseData(data, "Success", "200")
        }
    }
}