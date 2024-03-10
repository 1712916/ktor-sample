package com.vinhnt_study.db

import com.vinhnt_study.models.Account
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
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

object AccountTable : IntIdTable() {
     val account = varchar("account", 128)
    val password = varchar("password", 128)
    val email = varchar("email", 128)
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")
 }

class AccountEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AccountEntity>(AccountTable)
    var account by AccountTable.account
    var password     by AccountTable.password
    var email     by AccountTable.email
    var createDate     by AccountTable.createDate
    var updateDate     by AccountTable.updateDate
}

val iAccount = AccountEntity.new {
    account = "account_test"
    password = "password_test"
    email = "email_test"
}

fun main() {
    AccountEntity.new {
        account = "account_test"
        password = "password_test"
        email = "email_test"
    }

    AccountTable.insert {
        it[account] = "account_test"
        it[password] = "password_test"
        it[email] = "email_test"
    }
}