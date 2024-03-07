package com.vinhnt_study.db

import com.vinhnt_study.data.models.Accounts
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    fun init() {
        Class.forName("org.postgresql.Driver")
        val url = "jdbc:postgresql://localhost:32774/tracking_money"
        val user = "postgres"
        val password = "postgrespw"

        val database = Database.connect(
            url = url,
            user = user,
            password = password,
            driver = "org.postgresql.Driver",
        )

        transaction(database) {
            SchemaUtils.create(Accounts)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}