package com.vinhnt_study.services

import com.vinhnt_study.models.Category
import com.vinhnt_study.models.CategoryCount
import com.vinhnt_study.repositories.CategoryCountRepository
import com.vinhnt_study.repositories.CategoryCountRepositoryImpl
import com.vinhnt_study.repositories.CategoryRepository
import com.vinhnt_study.repositories.CategoryRepositoryImpl
import java.util.*

interface  CategoryCountService  {
    suspend fun countAll(account: String): List<CategoryCount>
    suspend fun countAll(account: String, startDate: Date, endDate: Date): List<CategoryCount>
    suspend fun countById(account: String, id: String): CategoryCount
    suspend fun countByIds(account: String, ids: List<String>): List<CategoryCount>
}

class CategoryCountServiceImpl : CategoryCountService {

    //declare category repository
    private val repository : CategoryCountRepository = CategoryCountRepositoryImpl()
    override suspend fun countAll(account: String): List<CategoryCount> {
        return repository.countAll(account)
    }

    override suspend fun countAll(account: String, startDate: Date, endDate: Date): List<CategoryCount> {
        return repository.countAll(account, startDate, endDate)
    }

    override suspend fun countById(account: String, id: String): CategoryCount {
        return repository.countById(account, id)
    }

    override suspend fun countByIds(account: String, ids: List<String>): List<CategoryCount> {
        if (ids.isEmpty()) {
            return  emptyList()
        }

        return repository.countByIds(account, ids)
    }
}