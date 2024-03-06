package com.vinhnt_study.data.models

import com.vinhnt_study.utils.DateSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
open class CreateDate(
    @Serializable(with = DateSerializer::class)
    val createDate: Date,
    @Serializable(with = DateSerializer::class)
    val updateDate: Date
)