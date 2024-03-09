package com.vinhnt_study.models

data class AuthData<T>(
    val accountId: String,
    val data: T,
)
