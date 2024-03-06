package com.vinhnt_study.data

import com.vinhnt_study.data.models.*
import java.util.*


//generate mock data for Money, Category, MoneySource
//gen list MoneySource
val mockMoneySources = listOf(
    MoneySource(id = UUID.randomUUID(), name = "Cash"),
    MoneySource(id = UUID.randomUUID(), name = "Bank"),
    MoneySource(id = UUID.randomUUID(), name = "Credit Card"),
)


//gen list Category
val mockExpenseCategories = listOf(
    ExpenseCategory(UUID.randomUUID(), "Food"),
    ExpenseCategory(UUID.randomUUID(), "Drink"),
    ExpenseCategory(UUID.randomUUID(), "Transport"),
    ExpenseCategory(UUID.randomUUID(), "Entertainment"),
    ExpenseCategory(UUID.randomUUID(), "Health"),
    ExpenseCategory(UUID.randomUUID(), "Education"),
    ExpenseCategory(UUID.randomUUID(), "Other"),
)

//gen list Money
val mockExpenses = mutableListOf(
    Money(
        UUID.randomUUID(),
        100000.0,
        MoneyType.Expense,
        mockExpenseCategories[0],
        "Lunch",
        mockMoneySources[0],
        Date(),
        Date(),
        Date()
    ),
    Money(
        UUID.randomUUID(),
        50000.0,
        MoneyType.Expense,
        mockExpenseCategories[1],
        "Milk Tea",
        mockMoneySources[0],
        Date(),
        Date(),
        Date()
    ),
    Money(
        UUID.randomUUID(),
        200000.0,
        MoneyType.Expense,
        mockExpenseCategories[2],
        "Bus",
        mockMoneySources[0],
        Date(),
        Date(),
        Date()
    ),
    Money(
        UUID.randomUUID(),
        100000.0,
        MoneyType.Expense,
        mockExpenseCategories[3],
        "Movie",
        mockMoneySources[0],
        Date(),
        Date(),
        Date()
    ),
    Money(
        UUID.randomUUID(),
        50000.0,
        MoneyType.Expense,
        mockExpenseCategories[4],
        "Medicine",
        mockMoneySources[2],
        Date(),
        Date(),
        Date()
    ),
    Money(
        UUID.randomUUID(),
        100000.0,
        MoneyType.Expense,
        mockExpenseCategories[5],
        "Book",
        mockMoneySources[2],
        Date(),
        Date(),
        Date()
    ),

    )
