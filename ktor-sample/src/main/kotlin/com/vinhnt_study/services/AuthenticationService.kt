package com.vinhnt_study.services

 import com.auth0.jwt.JWT
 import com.auth0.jwt.algorithms.Algorithm
 import com.vinhnt_study.models.Account
 import com.vinhnt_study.models.authentication.LoginResponse
 import com.vinhnt_study.repositories.AccountRepository
import com.vinhnt_study.repositories.AccountRepositoryImpl
 import org.mindrot.jbcrypt.BCrypt
 import java.util.*

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
        val existsAccount = repository.findByAccount(account) ?: throw IllegalArgumentException("Account not found")

        if (!BCrypt.checkpw(password, existsAccount.password)) {
            throw IllegalArgumentException("Password is incorrect")
        }

        val token = JWT.create()
            .withClaim("account_id", existsAccount.id.toString())
            .withClaim("account", existsAccount.account)
            .withClaim("email", existsAccount.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60000000))
            .sign(Algorithm.HMAC256("app-secret"))

        return LoginResponse(token)
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
            Account(
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
