package com.vinhnt_study.services

import com.vinhnt_study.models.Category
import com.vinhnt_study.repositories.CategoryRepository
import com.vinhnt_study.repositories.CategoryRepositoryImpl

interface  CategoryService : AuthDataService<Category, String>

class CategoryServiceImpl : CategoryService {

    //declare category repository
    private val repository : CategoryRepository = CategoryRepositoryImpl()

    override suspend fun findAll(accountId: String): List<Category> {
        return  repository.findAll(accountId)
    }

    override suspend fun findById(id: String, accountId: String): Category? {
        return  repository.findById(id, accountId)
    }

    override suspend fun add(item: Category, accountId: String): Category {
        return repository.add(item, accountId)
    }

    override suspend fun update(t: Category, accountId: String): Category {
        return repository.update(t, accountId)
    }

    override suspend fun delete(id: String, accountId: String): Boolean {
        return repository.delete(id, accountId)
    }
}