package com.vinhnt_study.repositories

import com.vinhnt_study.db.*
import com.vinhnt_study.models.*
import com.vinhnt_study.utils.LocaleDateTimeSerializer
import com.vinhnt_study.utils.MoneyTypeSerializer
import com.vinhnt_study.utils.toUUID
 import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import java.util.*


interface ExpenseRepository : AuthDataRepository<Money, String>

//expense repository implementation
class ExpenseRepositoryImpl : ExpenseRepository {
    private fun resultRowToMoney(row: ResultRow) = Money(
        id = row[Moneys.id].toString(),
        amount = row[Moneys.amount],
        type = MoneyType.entries[row[Moneys.type]],
        category = Category(
            id = row[Categories.id].toString(),
            name = row[Categories.name],
            type = MoneyType.entries[row[Categories.type]],
        ),
        source = MoneySource(
            id = row[MoneySources.id].toString(),
            name = row[MoneySources.name],
        ),
        description = row[Moneys.description],
        date = row[Moneys.date],
        createTime = row[Moneys.createDate],
        updateTime = row[Moneys.updateDate],
    )

    override suspend fun findAll(accountId: String): List<Money> = DatabaseSingleton.dbQuery {
        (Moneys innerJoin  Categories innerJoin MoneySources ).select { Moneys.accountId eq accountId.toUUID() }
            .map { resultRowToMoney(it) }
    }

    override suspend fun findById(id: String, accountId: String): Money?  = DatabaseSingleton.dbQuery {
        Moneys.innerJoin(Categories).innerJoin(MoneySources).select {
            Moneys.id eq id.toUUID() and (Moneys.accountId eq accountId.toUUID())
        }.map { resultRowToMoney(it) }.singleOrNull()
    }

    override suspend fun add(item: Money, accountId: String): Money= DatabaseSingleton.dbQuery {
        val now = LocalDateTime.now()

        val insertStatement = Moneys.insert {
            it[id] = UUID.randomUUID()
            it[amount] = item.amount
            it[type] = item.type.ordinal
            it[category] = item.category.id!!.toUUID()
            it[description] = item.description
            it[sourceId] = item.source.id!!.toUUID()
            it[date] = item.date!!
            it[createDate] = now
            it[updateDate] = now
            it[Moneys.accountId] = accountId.toUUID()
        }

        Moneys.innerJoin(Categories).innerJoin(MoneySources).select {
            Moneys.id eq insertStatement[Moneys.id]
        }.map { resultRowToMoney(it) }.singleOrNull()!!
    }
    override suspend fun update(item: Money, accountId: String): Money = DatabaseSingleton.dbQuery  {
        val now = LocalDateTime.now()
        val updatedRows = Moneys.update({ Moneys.id eq item.id!!.toUUID() and (Moneys.accountId eq accountId.toUUID()) }) {
            it[amount] = item.amount
            it[type] = item.type.ordinal
            it[category] = item.category.id!!.toUUID()
            it[description] = item.description
            it[sourceId] = item.source.id!!.toUUID()
            it[date] = item.date!!
            it[updateDate] = now
        }
        if (updatedRows > 0) {
            Moneys.innerJoin(Categories).innerJoin(MoneySources).select {
                Moneys.id eq item.id!!.toUUID()
            }.map { resultRowToMoney(it) }.single()
        } else {
            throw IllegalStateException("Money not found")
        }
    }

    override suspend fun delete(id: String, accountId: String): Boolean = DatabaseSingleton.dbQuery  {
        Moneys.deleteWhere { Moneys.id eq id.toUUID() and (Moneys.accountId eq accountId.toUUID()) } > 0
    }

}
