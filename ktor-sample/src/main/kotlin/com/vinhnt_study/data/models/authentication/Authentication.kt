package com.vinhnt_study.data.models.authentication

import kotlinx.serialization.Serializable

@Serializable
open class LoginRequest(
    val account: String, val password: String
)

class RegisterRequest(
    val email: String, account: String, password: String,
) : LoginRequest(account = account, password = password)