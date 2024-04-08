package com.vinhnt_study.repositories

import com.vinhnt_study.db.Categories
import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.db.Moneys
import com.vinhnt_study.models.CategoryMoney
import com.vinhnt_study.models.MoneyType
import com.vinhnt_study.utils.toLocalDateTime
import com.vinhnt_study.utils.toUUID
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.sum
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

interface ExpenseByCategoryRepository {
    suspend fun getListTotalExpenseByCategory(
        accountId: String,
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
        val fromDate = from.toLocalDateTime()
        val toDate = to.toLocalDateTime().plusDays(1)
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
}