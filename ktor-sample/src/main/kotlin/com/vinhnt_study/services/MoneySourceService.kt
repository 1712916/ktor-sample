package com.vinhnt_study.services

import com.vinhnt_study.models.MoneySource
import com.vinhnt_study.repositories.MoneySourceRepository
import com.vinhnt_study.repositories.MoneySourceRepositoryImpl
import java.util.UUID

interface MoneySourceService : AuthDataService<MoneySource, String>

//money source service implementation
class MoneySourceServiceImpl : MoneySourceService {
    private val repository : MoneySourceRepository = MoneySourceRepositoryImpl()


    private  fun getUUID(accountId: String): UUID {
        return UUID.fromString(accountId)
    }

    override suspend fun findAll(accountId: String): List<MoneySource> {
        return repository.findAll(getUUID(accountId))
    }

    override suspend fun findById(id: String, accountId: String): MoneySource? {
        return repository.findById(id, getUUID(accountId))
    }

    override suspend fun add(item: String, accountId: String): MoneySource {
        return repository.add(MoneySource(id = UUID.randomUUID(), name = item), getUUID(accountId))
    }

    override suspend fun update(t: MoneySource, accountId: String): MoneySource {
        return repository.update(t, getUUID(accountId))
    }

    override suspend fun delete(id: String, accountId: String): MoneySource {
        return repository.delete(id, getUUID(accountId))
    }
}
