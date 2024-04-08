package com.vinhnt_study.services

import com.vinhnt_study.models.DateMoney
import com.vinhnt_study.models.Money
import com.vinhnt_study.models.MonthMoney
import com.vinhnt_study.models.QuarterMoney
import com.vinhnt_study.repositories.ExpenseRepository
import com.vinhnt_study.repositories.ExpenseRepositoryImpl
import com.vinhnt_study.repositories.MonthTotalExpenseRepository
import com.vinhnt_study.repositories.TotalExpenseRepository
import com.vinhnt_study.utils.sortDate
import com.vinhnt_study.utils.toDate
import java.util.*

//create ExpenseService interface
interface TotalExpenseService {
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

interface MonthTotalExpenseService {

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


//create ExpenseServiceImpl class
class TotalExpenseServiceImpl : TotalExpenseService, MonthTotalExpenseService {
    private val repository: TotalExpenseRepository = ExpenseRepositoryImpl()
    private val totalMonthRepository: MonthTotalExpenseRepository = ExpenseRepositoryImpl()
    override suspend fun getTotalExpenseByDate(accountId: String, date: Date): Double {
        return repository.getTotalExpenseByDate(accountId, date)
    }

    override suspend fun getTotalExpenseByDates(accountId: String, from: Date, to: Date): Double {
        val dates = sortDate(from, to)
        return repository.getTotalExpenseByDates(accountId, dates.first, dates.second)
    }

    override suspend fun getListTotalExpenseByDates(accountId: String, from: Date, to: Date): List<DateMoney> {
        val dates = sortDate(from, to)
        return repository.getListTotalExpenseByDates(accountId, dates.first, dates.second)
    }

    override suspend fun getTotalExpenseByMonth(accountId: String, month: Int, year: Int): MonthMoney {
       return  totalMonthRepository.getTotalExpenseByMonth(accountId, month, year)
    }

    override suspend fun getTotalExpenseByMonthOfYear(accountId: String, year: Int): List<MonthMoney> {
        return totalMonthRepository.getTotalExpenseByMonthOfYear(accountId, year)
    }

    override suspend fun getTotalExpenseByQuater(accountId: String, quarter: Int, year: Int): QuarterMoney {
       return totalMonthRepository.getTotalExpenseByQuater(accountId, quarter, year)
    }

    override suspend fun getTotalExpenseByQuarterOfYear(accountId: String, year: Int): List<QuarterMoney> {
        return totalMonthRepository.getTotalExpenseByQuarterOfYear(accountId, year)
    }
}

