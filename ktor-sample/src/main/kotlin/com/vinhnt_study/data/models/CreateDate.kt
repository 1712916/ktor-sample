package com.vinhnt_study.data.models

import com.vinhnt_study.utils.DateSerializer
import com.vinhnt_study.utils.LocaleDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*

@Serializable
open class CreateDate(
     @Serializable(with = LocaleDateSerializer::class)
     val createDate: LocalDate,
     @Serializable(with = LocaleDateSerializer::class)
     val updateDate: LocalDate
)