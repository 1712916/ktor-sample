package com.vinhnt_study.data.models.authentication

import kotlinx.serialization.Serializable

@Serializable
open class LoginRequest(
    val account: String, val password: String
)

@Serializable
class RegisterRequest(
    val email: String, val account: String, val password: String
)

@Serializable
data class LoginResponse(val token: String)