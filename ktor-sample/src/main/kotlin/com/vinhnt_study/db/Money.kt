package com.vinhnt_study.db

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date

import org.jetbrains.exposed.sql.javatime.datetime

object Moneys : Table(){
    val id = uuid("id")
    val accountId = uuid("account_id").references(Accounts.id)
    val categoryId = uuid("category_id").references(Categories.id)
    val sourceId = uuid("source_id").references(MoneySources.id)
    val amount = double("amount")
    val type = integer("type")
    val description = varchar("description", 500).nullable()
    val date = date("date")
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}

object MoneySources : Table("money_sources") {
    val id = uuid("id")
    val accountId = uuid("account_id").references(Accounts.id)
    val name = varchar("name", 100)
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}

object Categories : Table() {
    val id = uuid("id")
    val accountId = uuid("account_id").references(Accounts.id)
    val type = integer("type")
    val name = varchar("name", 100)
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}


//object MoneySources2 : IntIdTable("money_sources_2") {
//    val accountId = reference("account_id", Accounts2.id)
//    val name = varchar("name", 100)
//    val createDate = datetime("create_date")
//    val updateDate = datetime("update_date")
// }