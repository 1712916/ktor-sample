package com.vinhnt_study.data.models

import com.vinhnt_study.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

class Account(
    @Serializable(with = UUIDSerializer::class) val id: UUID,
    val account: String,
    val password: String,
    val email: String,
    createDate: Date,
    updateDate: Date,
) : CreateDate(createDate, updateDate)
