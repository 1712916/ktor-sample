package com.vinhnt_study.services

import com.vinhnt_study.models.Money
import com.vinhnt_study.models.MoneyRequest
import com.vinhnt_study.repositories.ExpenseRepository

//create ExpenseService interface
interface ExpenseService : DataService<Money, MoneyRequest>

//create ExpenseServiceImpl class
class ExpenseServiceImpl  : ExpenseService  {
    private val repository = ExpenseRepository  ()
    override suspend fun findAll(): List<Money> {
        return  repository.findAll()
    }

    override suspend fun findById(id: String): Money? {
        return repository.findById(id)
     }

    override suspend fun add(item: MoneyRequest): Money {
        return repository.add(item)
    }

    override suspend fun update(t: Money): Money {
        return repository.update(t)
    }

    override suspend fun delete(id: String): Money {
        return repository.delete(id)
    }


}