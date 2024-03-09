package com.vinhnt_study.models

import com.vinhnt_study.db.DatabaseSingleton.dbQuery
import com.vinhnt_study.utils.LocaleDateTimeSerializer
import com.vinhnt_study.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
class Account(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val account: String,
    val password: String,
    val email: String,
    @Serializable(with = LocaleDateTimeSerializer::class)
    val createDate: LocalDateTime,
    @Serializable(with = LocaleDateTimeSerializer::class)
    val updateDate: LocalDateTime,
)

