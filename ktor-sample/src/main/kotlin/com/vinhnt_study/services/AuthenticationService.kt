package com.vinhnt_study.services

import com.vinhnt_study.data.models.authentication.RegisterRequest
import com.vinhnt_study.data.repositories.AccountRepository
import com.vinhnt_study.data.repositories.AccountRepositoryImpl

interface AuthenticationService {
    suspend fun loginByAccount(account: String, password: String)

    suspend fun loginByEmail(email: String, password: String)

    suspend fun register(account: String, email: String, password: String)

    suspend fun changePassword(account: String, oldPassword: String, newPassword: String)

    suspend fun resetPassword(email: String, newPassword: String)

    suspend fun logout()
}

class  AuthenticationServiceImpl : AuthenticationService  {
    private val repository : AccountRepository =  AccountRepositoryImpl()

    override suspend fun loginByAccount(account: String, password: String) {
        repository.findByAccount(account)?.let {
            if (it.password == password) {
                TODO("USE BCRYPT TO COMPARE PASSWORDS")
            }
        }

        throw IllegalArgumentException("Account not found")
    }

    override suspend fun loginByEmail(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun register(account: String, email: String, password: String) {
        repository.findByAccount(account)?.let {
            throw IllegalArgumentException("Account already exists")
        }

        repository.findByEmail(email)?.let {
            throw IllegalArgumentException("Email already exists")
        }

        repository.add(
            RegisterRequest(
                account,
                password,
                email
            )
        )
    }

    override suspend fun changePassword(account: String, oldPassword: String, newPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(email: String, newPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

}
