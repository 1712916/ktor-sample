package com.vinhnt_study.services

import com.vinhnt_study.models.CategoryMoney
import com.vinhnt_study.models.DateMoney
import com.vinhnt_study.repositories.ExpenseByCategoryRepository
import com.vinhnt_study.repositories.ExpenseByCategoryRepositoryImpl
import java.util.*

interface TotalExpenseCategoryService {
    suspend fun getListTotalExpenseByCategory(
        accountId: String,
        from: Date,
        to: Date,
    ): List<CategoryMoney>
}

class TotalExpenseCategoryServiceImpl : TotalExpenseCategoryService {
    private val expenseByCategoryRepository: ExpenseByCategoryRepository = ExpenseByCategoryRepositoryImpl()

    override suspend fun getListTotalExpenseByCategory(accountId: String, from: Date, to: Date): List<CategoryMoney> {
        return  expenseByCategoryRepository.getListTotalExpenseByCategory(accountId, from, to)
    }

}