package com.vinhnt_study.db

import com.vinhnt_study.models.Account
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object Accounts : Table() {
    val id = uuid("id")
    val account = varchar("account", 128)
    val password = varchar("password", 128)
    val email = varchar("email", 128)
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}