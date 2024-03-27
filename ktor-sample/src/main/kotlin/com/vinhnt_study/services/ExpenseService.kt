package com.vinhnt_study.services

import com.vinhnt_study.models.Money
import com.vinhnt_study.repositories.ExpenseRepository
import com.vinhnt_study.repositories.ExpenseRepositoryImpl
import com.vinhnt_study.utils.toDate

//create ExpenseService interface
interface ExpenseService : AuthDataService<Money, String> {
    suspend fun search(
        accountId: String,
        fromDate: String,
        toDate: String,
        categoryIds: List<String>? = null,
        sourceIds: List<String>? = null
    ): List<Money>

    suspend fun getExpenseListByDate(accountId: String, date: String): List<Money>
}

//create ExpenseServiceImpl class
class ExpenseServiceImpl : ExpenseService {
    private val repository: ExpenseRepository = ExpenseRepositoryImpl()
    override suspend fun search(
        accountId: String, fromDate: String, toDate: String, categoryIds: List<String>?, sourceIds: List<String>?
    ): List<Money> {
        return repository.search(accountId, fromDate.toDate(), toDate.toDate(), categoryIds, sourceIds)
    }

    override suspend fun getExpenseListByDate(accountId: String, date: String): List<Money> {
        //validate date

        //is valid
        return repository.getExpenseListByDate(accountId, date.toDate())
    }

    override suspend fun findAll(accountId: String): List<Money> {
        return repository.findAll(accountId)
    }

    override suspend fun findById(id: String, accountId: String): Money? {
        return repository.findById(id, accountId)
    }

    override suspend fun add(item: Money, accountId: String): Money {
        return repository.add(item, accountId)
    }

    override suspend fun update(t: Money, accountId: String): Money {
        return repository.update(t, accountId)
    }

    override suspend fun delete(id: String, accountId: String): Boolean {
        return repository.delete(id, accountId)
    }
}