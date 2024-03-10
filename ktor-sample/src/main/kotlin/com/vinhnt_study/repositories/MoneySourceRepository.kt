package com.vinhnt_study.repositories

import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.db.MoneySources
import com.vinhnt_study.models.MoneySource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import java.util.UUID

interface MoneySourceRepository : AuthDataRepository<MoneySource, String>
class MoneySourceRepositoryImpl : MoneySourceRepository {
    private fun resultRowToMoneySource(row: ResultRow) = MoneySource(
        id = row[MoneySources.id].toString(),
        name = row[MoneySources.name],
    )

    override suspend fun findAll(accountId: String): List<MoneySource> = DatabaseSingleton.dbQuery {
        MoneySources.select { MoneySources.accountId eq UUID.fromString(accountId) }.map { resultRowToMoneySource(it) }
    }

    override suspend fun findById(id: String, accountId: String): MoneySource? = DatabaseSingleton.dbQuery {
        MoneySources.select {
                MoneySources.id eq UUID.fromString(id) and (MoneySources.accountId eq UUID.fromString(
                    accountId
                ))
            }.map { resultRowToMoneySource(it) }.singleOrNull()
    }

    override suspend fun delete(id: String, accountId: String): Boolean = DatabaseSingleton.dbQuery {
        MoneySources.deleteWhere { MoneySources.id eq UUID.fromString(id) } > 0
    }

    override suspend fun update(item: MoneySource, accountId: String): MoneySource = DatabaseSingleton.dbQuery {
        val moneySourceId = item.id

        val updatedRows = MoneySources.update(where = {
            (MoneySources.id eq UUID.fromString(moneySourceId)) and (MoneySources.accountId eq UUID.fromString(
                accountId
            ))
        }) {
            it[name] = item.name
            it[updateDate] = LocalDateTime.now()
        }

        if (updatedRows > 0) {
            MoneySources.select { MoneySources.id eq UUID.fromString(moneySourceId) }.map { resultRowToMoneySource(it) }
                .single()
        } else {
            throw IllegalStateException("Money source not found")
        }
    }

    override suspend fun add(item: MoneySource, accountId: String): MoneySource = DatabaseSingleton.dbQuery {
        val insertStatement = MoneySources.insert {
            it[id] = UUID.randomUUID()
            it[name] = item.name
            it[createDate] = LocalDateTime.now()
            it[updateDate] = LocalDateTime.now()
            it[MoneySources.accountId] = UUID.fromString(accountId)
        }
        insertStatement.resultedValues!!.singleOrNull()!!.let(::resultRowToMoneySource)
    }
}