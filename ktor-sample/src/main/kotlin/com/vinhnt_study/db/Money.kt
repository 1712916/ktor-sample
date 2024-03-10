package com.vinhnt_study.db

import com.vinhnt_study.models.AuthData
import com.vinhnt_study.models.Category
import com.vinhnt_study.models.MoneySource
import com.vinhnt_study.models.MoneyType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object Moneys : Table(){
    val id = uuid("id")
    val accountId = reference("account_id", Accounts.id)
    val amount = double("amount")
    val type = integer("type")
    val category = reference("category_id", Categories.id)
    val description = varchar("description", 500)
    val sourceId = reference("source_id", MoneySources.id)
    val date = datetime("date")
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}

object MoneySources : Table("money_sources") {
    val id = uuid("id")
    val accountId = reference("account_id", Accounts.id)
    val name = varchar("name", 100)
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}

object Categories : Table() {
    val id = uuid("id")
    val accountId = reference("account_id", Accounts.id)
    val type = integer("type")
    val name = varchar("name", 100)
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}
