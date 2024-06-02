package com.vinhnt_study.repositories

import com.vinhnt_study.db.Categories
import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.db.Moneys
import com.vinhnt_study.models.CategoryMoney
import com.vinhnt_study.models.MoneyType
import com.vinhnt_study.utils.toLocalDate
import com.vinhnt_study.utils.toLocalDateTime
import com.vinhnt_study.utils.toUUID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

interface ExpenseByCategoryRepository {
    suspend fun getListTotalExpenseByCategory(
        accountId: String,
        from: Date,
        to: Date,
    ): List<CategoryMoney>

    suspend fun getListTotalExpenseByCategoryIds(
        accountId: String,
        idList: List<String>,
        from: Date,
        to: Date,
    ): List<CategoryMoney>
}

class ExpenseByCategoryRepositoryImpl : ExpenseByCategoryRepository {

    override suspend fun getListTotalExpenseByCategory(
        accountId: String,
        from: Date,
        to: Date,
    ): List<CategoryMoney> = DatabaseSingleton.dbQuery  {
        val fromDate = from.toLocalDate()
        val toDate = to.toLocalDate().plusDays(1)
        Moneys.innerJoin(Categories).slice(
           Categories.id, Categories.name, Moneys.amount.sum(),
       ).select {
            (Moneys.date greaterEq fromDate) and (Moneys.date lessEq  toDate) and (Moneys.accountId eq accountId.toUUID())
        }
           .groupBy(Categories.id).map {
               row -> CategoryMoney(
                   id = row[Categories.id].toString(),
                   name = row[Categories.name],
                   amount = row[Moneys.amount.sum()] ?: 0.0,
                   type = MoneyType.Expense,
               )
           }
    }

    override suspend fun getListTotalExpenseByCategoryIds(
        accountId: String,
        idList: List<String>,
        from: Date,
        to: Date
    ): List<CategoryMoney> = DatabaseSingleton.dbQuery {
        val categoryUUIDList: List<UUID> = idList.map { it.trim().toUUID() }.toList()

        val fromDate = from.toLocalDate()
        val toDate = to.toLocalDate().plusDays(1)
        Moneys.innerJoin(Categories).slice(
            Categories.id, Categories.name, Moneys.amount.sum(),
        ).select {
            (Moneys.date greaterEq fromDate) and
            (Moneys.date lessEq  toDate) and
            (Moneys.accountId eq accountId.toUUID()) and
            (if (categoryUUIDList.isNotEmpty()) categoryUUIDList.let { Moneys.categoryId inList it }  else Op.TRUE)
        }
            .groupBy(Categories.id).map {
                    row -> CategoryMoney(
                id = row[Categories.id].toString(),
                name = row[Categories.name],
                amount = row[Moneys.amount.sum()] ?: 0.0,
                type = MoneyType.Expense,
            )
            }
    }
}