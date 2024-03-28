package com.vinhnt_study.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.server.application.*
import io.ktor.server.routing.*
object DatabaseSingleton {
    fun init(url: String, user: String, password: String) {
        Class.forName("org.postgresql.Driver")

        val database = Database.connect(
            url = url,
            user = user,
            password = password,
            driver = "org.postgresql.Driver",
        )

        transaction(database) {
            SchemaUtils.create(Accounts)
            SchemaUtils.create(MoneySources, Categories, Moneys)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}