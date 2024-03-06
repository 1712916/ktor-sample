package com.vinhnt_study.data.models

import com.vinhnt_study.utils.*
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

//money: expense, income
@Serializable
data class Money(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val amount: Double,
    @Serializable(with = MoneyTypeSerializer::class)
    val type: MoneyType,
    val category: Category,
    val description: String,
    val source: MoneySource,
    @Serializable(with = DateSerializer::class)
    val date: Date,
    @Serializable(with = DateSerializer::class)
    val createTime: Date,
    @Serializable(with = DateSerializer::class)
    val updateTime: Date,
) {
    //update current money with new money data
    fun update(newMoney: Money): Money {
        return Money(
            id = this.id,
            amount = newMoney.amount,
            type = newMoney.type,
            category = newMoney.category,
            description = newMoney.description,
            source = newMoney.source,
            date = newMoney.date,
            createTime = this.createTime,
            updateTime = Date()
        )
    }
}

@Serializable
data class MoneyRequest(
    val amount: Double,
    val type: Int,
    val categoryId: String,
    val description: String,
    val sourceId: String,
    @Serializable(with = DateSerializer::class)
    val date: Date,
)
//expense category: food, drink, ...
enum class MoneyType {
    Expense, Income
}

//storage money from: cash, bank, credit card, ...
@Serializable
data class MoneySource(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
)

