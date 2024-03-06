package com.vinhnt_study.data.repositories

import com.vinhnt_study.data.models.Account
import com.vinhnt_study.data.models.Money
import com.vinhnt_study.data.models.MoneyRequest
import com.vinhnt_study.data.models.authentication.LoginRequest


//create AuthenticationRepository
class AuthenticationRepository : DataRepository<Account, LoginRequest>()  {
    override fun findAll(): List<Account> {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): Account? {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Account {
        TODO("Not yet implemented")
    }

    override fun update(t: Account): Account {
        TODO("Not yet implemented")
    }

    override fun add(t: LoginRequest): Account {
        TODO("Not yet implemented")
    }

}

