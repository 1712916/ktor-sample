package com.vinhnt_study.services

import com.vinhnt_study.models.MoneySource
import com.vinhnt_study.repositories.MoneySourceRepository
import com.vinhnt_study.repositories.MoneySourceRepositoryImpl

interface MoneySourceService : AuthDataService<MoneySource, String>

//money source service implementation
class MoneySourceServiceImpl : MoneySourceService {
    private val repository : MoneySourceRepository = MoneySourceRepositoryImpl()

    override suspend fun findAll(accountId: String): List<MoneySource> {
        return repository.findAll(accountId)
    }

    override suspend fun findById(id: String, accountId: String): MoneySource? {
        return repository.findById(id, accountId)
    }

    override suspend fun add(item: MoneySource, accountId: String): MoneySource {
        return repository.add(item, accountId)
    }

    override suspend fun update(t: MoneySource, accountId: String): MoneySource {
        return repository.update(t, accountId)
    }

    override suspend fun delete(id: String, accountId: String): Boolean {
        return repository.delete(id, accountId)
    }
}
