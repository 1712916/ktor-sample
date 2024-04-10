package com.vinhnt_study.models

import com.vinhnt_study.utils.MoneyTypeSerializer
import kotlinx.serialization.Serializable

@Serializable
class CategoryCount(
    val id: String? = null,
    val name: String,
    val count: Long,
)