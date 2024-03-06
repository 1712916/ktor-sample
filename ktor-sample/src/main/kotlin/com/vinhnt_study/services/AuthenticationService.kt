package com.vinhnt_study.services

import com.vinhnt_study.data.models.Account
import com.vinhnt_study.data.models.authentication.LoginRequest
import com.vinhnt_study.data.repositories.AuthenticationRepository

interface AuthenticationService : DataService<Account, LoginRequest>{
}

class AuthenticationServiceImpl : AuthenticationService {
    private val repository = AuthenticationRepository()
    override fun findAll(): List<Account> {
        return repository.findAll()
    }

    override fun findById(id: String): Account? {
        return repository.findById(id)
    }

    override fun add(item: LoginRequest): Account {
        return repository.add(item)
    }

    override fun update(t: Account): Account {
        return repository.update(t)
    }

    override fun delete(id: String): Account {
        return repository.delete(id)
    }
}