package com.vinhnt_study.models

import com.vinhnt_study.utils.DateSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class DateMoney(
    @Serializable(with = DateSerializer::class)
    val date: Date,
    val amount: Double,
)
@Serializable
data class MonthMoney(
    val month: Int,
    val year: Int,
    val amount: Double,
)

@Serializable
data class QuarterMoney(
    val quarter: Int,
    val year: Int,
    val amount: Double,
)