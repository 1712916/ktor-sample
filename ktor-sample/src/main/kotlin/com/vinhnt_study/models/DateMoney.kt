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