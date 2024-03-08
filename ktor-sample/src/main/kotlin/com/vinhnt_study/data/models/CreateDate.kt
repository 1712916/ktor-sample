package com.vinhnt_study.data.models

import com.vinhnt_study.utils.LocaleDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
open class CreateDate(
     @Serializable(with = LocaleDateTimeSerializer::class)
     val createDate: LocalDateTime,
     @Serializable(with = LocaleDateTimeSerializer::class)
     val updateDate: LocalDateTime
)