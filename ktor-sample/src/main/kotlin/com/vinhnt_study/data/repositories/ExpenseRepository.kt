package com.vinhnt_study.data.repositories

import com.vinhnt_study.data.models.*
import io.ktor.server.plugins.*
import java.util.*


class ExpenseRepository : DataRepository<Money, MoneyRequest>() {

    private val expenses = mutableListOf<Money>()

    init {
        //generate some dummy data for expenses
        expenses.add(
            Money(
                UUID.randomUUID(),
                35000.0,
                MoneyType.Expense,
                ExpenseCategory(UUID.randomUUID(), "Food"),
                "Lunch",
                MoneySource(id = UUID.randomUUID(), name = "Cash"),
                Date(),
                Date(),
                Date()
            ),
        )

        expenses.add(
            Money(
                UUID.randomUUID(),
                100000.0,
                MoneyType.Expense,
                ExpenseCategory(UUID.randomUUID(), "Drink"),
                "Coffee",
                MoneySource(id = UUID.randomUUID(), name = "Cash"),
                Date(),
                Date(),
                Date()
            ),
        )

        expenses.add(
            Money(
                UUID.randomUUID(),
                50000.0,
                MoneyType.Expense,
                ExpenseCategory(UUID.randomUUID(), "Drink"),
                "Milk Tea",
                MoneySource(id = UUID.randomUUID(), name = "Cash"),
                Date(),
                Date(),
                Date()
            ),
        )
    }

    override fun findAll(): List<Money> {
        return expenses
    }

    override fun findById(id: String): Money? {
        return expenses.firstOrNull { it.id == UUID.fromString(id) }
    }

    override fun delete(id: String): Money {
        val money = findById(id) ?: throw NotFoundException("Can not find money with id $id")

        expenses.remove(money)

        return money
    }


    override fun update(t: Money): Money {
        //update money
        val index = expenses.indexOfFirst { it.id == t.id }
        if (index < 0) {
            throw NotFoundException("Can not find money with id ${t.id}")
        }
        expenses[index] = expenses[index].update(t)
        return expenses[index]
    }

    override fun add(t: MoneyRequest): Money {
        //create money from money request
        val money = Money(
            UUID.randomUUID(),
            t.amount,
            MoneyType.entries[t.type],
            ExpenseCategory(UUID.fromString(t.categoryId), "Food"),
            t.description,
            MoneySource(UUID.fromString(t.sourceId), "Cash"),
            t.date,
            Date(),
            Date()
        )
        expenses.add(money)
        return money
    }
}

