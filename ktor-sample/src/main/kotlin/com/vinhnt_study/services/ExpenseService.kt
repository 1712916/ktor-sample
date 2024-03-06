package com.vinhnt_study.services

import com.vinhnt_study.data.models.Money
import com.vinhnt_study.data.models.MoneyRequest
import com.vinhnt_study.data.repositories.ExpenseRepository



//create a data service interface
interface DataService<T, R> {
    fun findAll(): List<T>
    fun findById(id: String): T?
    fun add(item: R): T
    fun update(t: T): T
    fun delete(id: String): T
}

//create ExpenseService interface
interface ExpenseService : DataService<Money, MoneyRequest>

//create ExpenseServiceImpl class
class ExpenseServiceImpl  : ExpenseService  {
    private val repository = ExpenseRepository  ()
    override fun findAll(): List<Money> {
        return  repository.findAll()
    }

    override fun findById(id: String): Money? {
        return repository.findById(id)
     }

    override fun add(item: MoneyRequest): Money {
        return repository.add(item)
    }

    override fun update(t: Money): Money {
        return repository.update(t)
    }

    override fun delete(id: String): Money {
        return repository.delete(id)
    }


}