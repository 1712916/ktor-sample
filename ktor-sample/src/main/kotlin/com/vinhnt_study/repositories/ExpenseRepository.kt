package com.vinhnt_study.repositories

import com.vinhnt_study.db.*
import com.vinhnt_study.models.*
import com.vinhnt_study.utils.*
import com.vinhnt_study.utils.toLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import java.util.*

interface TotalExpenseRepository {
    suspend fun getTotalExpenseByDate(
        accountId: String,
        date: Date,
    ): Double

    suspend fun getTotalExpenseByDates(
        accountId: String,
        from: Date,
        to: Date,
    ): Double

    suspend fun getListTotalExpenseByDates(
        accountId: String,
        from: Date,
        to: Date,
    ): List<DateMoney>
}
interface MonthTotalExpenseRepository {

    suspend fun getTotalExpenseByMonth(
        accountId: String,
        month: Int,
        year: Int,
    ): MonthMoney

    suspend fun getTotalExpenseByMonthOfYear(
        accountId: String,
        year: Int,
    ): List<MonthMoney>

    suspend fun getTotalExpenseByQuater(
        accountId: String,
        quarter: Int,
        year: Int,
    ): QuarterMoney

    suspend fun getTotalExpenseByQuarterOfYear(
        accountId: String,
        year: Int,
    ): List<QuarterMoney>
}

interface ExpenseRepository : AuthDataRepository<Money, String> {
    //search from date to date
    suspend fun search(
        accountId: String,
        fromDate: Date,
        toDate: Date,
        categoryIds: List<String>? = null,
        sourceId: List<String>? = null,
    ): List<Money>

    suspend fun getExpenseListByDate(
        accountId: String,
        date: Date,
    ): List<Money>


}

//expense repository implementation
class ExpenseRepositoryImpl : ExpenseRepository, TotalExpenseRepository, MonthTotalExpenseRepository {
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

    override suspend fun search(
        accountId: String, fromDate: Date, toDate: Date, categoryIds: List<String>?, sourceId: List<String>?
    ): List<Money> = DatabaseSingleton.dbQuery {
        val categoryUUIDList: List<UUID>? = categoryIds?.map { it.trim().toUUID() }?.toList()
        val sourceUUIDList: List<UUID>? = sourceId?.map { it.trim().toUUID() }?.toList()
        Moneys.innerJoin(Categories).innerJoin(MoneySources).select {
            (Moneys.date greaterEq fromDate.toLocalDateTime()) and (Moneys.date lessEq toDate.toLocalDateTime()) and (Moneys.accountId eq accountId.toUUID())
        }.filter {
            //TODO("create a flag to handle condition or, and")

            (categoryUUIDList == null || it[Moneys.categoryId] in categoryUUIDList) || (sourceUUIDList == null || it[Moneys.sourceId] in sourceUUIDList)
        }

            .map { resultRowToMoney(it) }.toList()
    }

    override suspend fun getExpenseListByDate(accountId: String, date: Date): List<Money> = DatabaseSingleton.dbQuery {
        val currentDate = date.toLocalDateTime()
        val nextDate = currentDate.plusDays(1)

        Moneys.innerJoin(Categories).innerJoin(MoneySources).select {
            (Moneys.date greaterEq currentDate) and (Moneys.date less nextDate) and (Moneys.accountId eq accountId.toUUID())
        }.map { resultRowToMoney(it) }.toList()
    }

    override suspend fun getTotalExpenseByDate(accountId: String, date: Date): Double = DatabaseSingleton.dbQuery {
        val currentDate = date.toLocalDateTime()
        val nextDate = currentDate.plusDays(1)

        Moneys
            .slice(
               Moneys.amount.sum(),
            )
            .select {
            (Moneys.date greaterEq currentDate) and (Moneys.date less nextDate) and (Moneys.accountId eq accountId.toUUID())
        }.single()[Moneys.amount.sum()] ?: 0.0
    }

    override suspend fun getTotalExpenseByDates(accountId: String, from: Date, to: Date): Double  = DatabaseSingleton.dbQuery {
        val fromDate = from.toLocalDateTime()
        val toDate = to.toLocalDateTime().plusDays(1)
        Moneys
            .slice(
                Moneys.amount.sum(),
            )
            .select {
                (Moneys.date greaterEq fromDate) and (Moneys.date less toDate) and (Moneys.accountId eq accountId.toUUID())
            }.single()[Moneys.amount.sum()] ?: 0.0
    }

    override suspend fun getListTotalExpenseByDates(accountId: String, from: Date, to: Date): List<DateMoney> {
        val days = getAllDaysBetweenDates(from, to)

        val list = mutableListOf<DateMoney>()

        days.forEach {
            list.add(DateMoney(date = it, amount =  getTotalExpenseByDate(accountId, it)))
        }

        return list
    }


    override suspend fun findAll(accountId: String): List<Money> = DatabaseSingleton.dbQuery {
        (Moneys innerJoin Categories innerJoin MoneySources).select { Moneys.accountId eq accountId.toUUID() }
            .map { resultRowToMoney(it) }
    }

    override suspend fun findById(id: String, accountId: String): Money? = DatabaseSingleton.dbQuery {
        Moneys.innerJoin(Categories).innerJoin(MoneySources).select {
            Moneys.id eq id.toUUID() and (Moneys.accountId eq accountId.toUUID())
        }.map { resultRowToMoney(it) }.singleOrNull()
    }

    override suspend fun add(item: Money, accountId: String): Money = DatabaseSingleton.dbQuery {
        val now = LocalDateTime.now()

        val insertStatement = Moneys.insert {
            it[id] = UUID.randomUUID()
            it[amount] = item.amount
            it[type] = item.type.ordinal
            it[categoryId] = item.category.id!!.toUUID()
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

    override suspend fun update(item: Money, accountId: String): Money = DatabaseSingleton.dbQuery {
        val now = LocalDateTime.now()
        val updatedRows =
            Moneys.update({ Moneys.id eq item.id!!.toUUID() and (Moneys.accountId eq accountId.toUUID()) }) {
                it[amount] = item.amount
                it[type] = item.type.ordinal
                it[categoryId] = item.category.id!!.toUUID()
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

    override suspend fun delete(id: String, accountId: String): Boolean = DatabaseSingleton.dbQuery {
        Moneys.deleteWhere { Moneys.id eq id.toUUID() and (Moneys.accountId eq accountId.toUUID()) } > 0
    }

    override suspend fun getTotalExpenseByMonth(accountId: String, month: Int, year: Int): MonthMoney  {
       val  dates = DateUtils.getStartAndEndDateOfMonth(month, year)

       val amount = getTotalExpenseByDates(accountId, dates.first, dates.second)

        return  MonthMoney(month = month, year = year, amount = amount)
    }

    override suspend fun getTotalExpenseByMonthOfYear(accountId: String, year: Int): List<MonthMoney> {
        return  (1..12).map {
            getTotalExpenseByMonth(accountId, it , year)
        }
    }

    override suspend fun getTotalExpenseByQuater(accountId: String, quarter: Int, year: Int): QuarterMoney = DatabaseSingleton.dbQuery {
       val q = DateUtils.getQuarterRange(year, quarter)

        val fromDate = q.first.toLocalDateTime()
        val toDate = q.second.toLocalDateTime().plusDays(1)
        val data = Moneys
            .slice(
                Moneys.amount.sum(),
            )
            .select {
                (Moneys.date greaterEq fromDate) and (Moneys.date less toDate) and (Moneys.accountId eq accountId.toUUID())
            }.single()

        QuarterMoney(quarter = quarter, year = year, amount = data[Moneys.amount.sum()] ?: 0.0)
    }

    override suspend fun getTotalExpenseByQuarterOfYear(accountId: String, year: Int): List<QuarterMoney> {
        return  (1..4).map {
            getTotalExpenseByQuater(accountId, it, year)
        }
    }
}
