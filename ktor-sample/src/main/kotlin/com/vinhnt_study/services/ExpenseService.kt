package com.vinhnt_study.services

import com.vinhnt_study.models.Money
import com.vinhnt_study.models.MoneyRequest
import com.vinhnt_study.repositories.ExpenseRepository

//create ExpenseService interface
interface ExpenseService : AuthDataService<Money, String>

//create ExpenseServiceImpl class
class ExpenseServiceImpl  : ExpenseService  {
    private val repository = ExpenseRepository  ()
    override suspend fun findAll(accountId: String): List<Money> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: String, accountId: String): Money? {
        TODO("Not yet implemented")
    }

    override suspend fun add(item: Money, accountId: String): Money {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Money, accountId: String): Money {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: String, accountId: String): Boolean {
        TODO("Not yet implemented")
    }
}