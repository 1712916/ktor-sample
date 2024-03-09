package com.vinhnt_study.repositories

import com.vinhnt_study.data.mockExpenseCategories
import com.vinhnt_study.data.mockExpenses
import com.vinhnt_study.data.mockMoneySources
import com.vinhnt_study.models.Money
import com.vinhnt_study.models.MoneyRequest
import com.vinhnt_study.models.MoneyType
import io.ktor.server.plugins.*
import java.util.*

class ExpenseRepository : DataRepository<Money, MoneyRequest> {

    private val expenses = mutableListOf<Money>()

    init {
        expenses.addAll(mockExpenses)
    }

    override suspend fun findAll(): List<Money> {
        return expenses
    }

    override suspend fun findById(id: String): Money? {
        return expenses.firstOrNull { it.id == UUID.fromString(id) }
    }

    override suspend fun delete(id: String): Money {
        val money = findById(id) ?: throw NotFoundException("Can not find money with id $id")

        expenses.remove(money)

        return money
    }


    override suspend fun update(t: Money): Money {
        //update money
        val index = expenses.indexOfFirst { it.id == t.id }
        if (index < 0) {
            throw NotFoundException("Can not find money with id ${t.id}")
        }
        expenses[index] = expenses[index].update(t)
        return expenses[index]
    }

    override suspend fun add(t: MoneyRequest): Money {
        //create money from money request
        val money = Money(
            UUID.randomUUID(),
            t.amount,
            MoneyType.entries[t.type],
            mockExpenseCategories.firstOrNull { it.id == UUID.fromString(t.categoryId) } ?: throw NotFoundException("Can not find category with id ${t.categoryId}"),
            t.description,
            mockMoneySources.firstOrNull { it.id == UUID.fromString(t.sourceId) } ?: throw NotFoundException("Can not find source with id ${t.sourceId}"),
            t.date,
            Date(),
            Date()
        )
        expenses.add(money)
        return money
    }
}

