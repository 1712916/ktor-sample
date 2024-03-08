package com.vinhnt_study.services

 import com.vinhnt_study.data.models.authentication.LoginResponse
 import com.vinhnt_study.data.models.authentication.RegisterRequest
import com.vinhnt_study.data.repositories.AccountRepository
import com.vinhnt_study.data.repositories.AccountRepositoryImpl
 import org.mindrot.jbcrypt.BCrypt

interface AuthenticationService {
    suspend fun loginByAccount(account: String, password: String) : LoginResponse

    suspend fun loginByEmail(email: String, password: String) : LoginResponse

    suspend fun register(account: String, email: String, password: String)

    suspend fun changePassword(account: String, oldPassword: String, newPassword: String)

    suspend fun resetPassword(email: String, newPassword: String)

    suspend fun logout()
}

class  AuthenticationServiceImpl : AuthenticationService  {
    private val repository : AccountRepository =  AccountRepositoryImpl()

    override suspend fun loginByAccount(account: String, password: String) : LoginResponse {
        val account = repository.findByAccount(account) ?: throw IllegalArgumentException("Account not found")

        if (!BCrypt.checkpw(password, account.password)) {
            throw IllegalArgumentException("Password is incorrect")
        }

        return LoginResponse("token")
    }

    override suspend fun loginByEmail(email: String, password: String) : LoginResponse {
        return LoginResponse("token")
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
                email = email,
                account = account,
                password = BCrypt.hashpw(password, BCrypt.gensalt()),
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
