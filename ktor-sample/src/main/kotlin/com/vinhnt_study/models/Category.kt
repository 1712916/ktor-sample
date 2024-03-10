package com.vinhnt_study.models

import com.vinhnt_study.utils.LocaleDateTimeSerializer
import com.vinhnt_study.utils.MoneyTypeSerializer
import com.vinhnt_study.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Serializable
open class Category (
    val id: String? = null,
    val name: String,
    @Serializable(with = MoneyTypeSerializer::class)
    val type: MoneyType,
)

