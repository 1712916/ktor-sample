package com.vinhnt_study.services

import com.vinhnt_study.models.DateMoney
import com.vinhnt_study.models.Money
import com.vinhnt_study.repositories.ExpenseRepository
import com.vinhnt_study.repositories.ExpenseRepositoryImpl
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

//create ExpenseServiceImpl class
class TotalExpenseServiceImpl : TotalExpenseService {
    private val repository: TotalExpenseRepository = ExpenseRepositoryImpl()
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
}

