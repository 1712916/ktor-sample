package com.vinhnt_study.models

import com.vinhnt_study.utils.MoneyTypeSerializer
import kotlinx.serialization.Serializable

@Serializable
class CategoryMoney(
    val id: String? = null,
    val name: String,
    @Serializable(with = MoneyTypeSerializer::class)
    val type: MoneyType,
    val amount: Double,
)