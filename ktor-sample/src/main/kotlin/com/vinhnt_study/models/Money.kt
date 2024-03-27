package com.vinhnt_study.models

import com.vinhnt_study.utils.*
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID



@Serializable
data class Money(
     val id: String? = null,
    val amount: Double,
    @Serializable(with = MoneyTypeSerializer::class)
    val type: MoneyType,
    val category: Category,
    val description: String? = null,
    val source: MoneySource,
    @Serializable(with = LocaleDateTimeSerializer::class)
    val date: LocalDateTime? = null,
     @Serializable(with = LocaleDateTimeSerializer::class)
    val createTime: LocalDateTime? = null,
     @Serializable(with = LocaleDateTimeSerializer::class)
    val updateTime:  LocalDateTime? = null,
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
     val id: String? = null,
    val name: String,
)

