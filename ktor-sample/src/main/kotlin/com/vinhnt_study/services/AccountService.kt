package com.vinhnt_study.services

import com.vinhnt_study.data.models.Account
import com.vinhnt_study.data.models.authentication.RegisterRequest
import com.vinhnt_study.data.repositories.AccountRepository
import com.vinhnt_study.data.repositories.AccountRepositoryImpl

interface AccountService : DataService<Account, RegisterRequest>{
}

class AccountServiceImpl : AccountService {
    private val repository :AccountRepository =  AccountRepositoryImpl()
    override suspend  fun findAll(): List<Account> {
        return repository.findAll()
    }

    override suspend fun findById(id: String): Account? {
        return repository.findById(id)
    }

    override suspend fun add(item: RegisterRequest): Account {
        return repository.add(item)
    }

    override suspend fun update(t: Account): Account {
        return repository.update(t)
    }

    override suspend fun delete(id: String): Account {
        return repository.delete(id)
    }
}