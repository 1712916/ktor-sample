package com.vinhnt_study.repositories

interface DataRepository<T, ID> {
    suspend fun findAll(): List<T>
    suspend fun findById(id: ID): T?
    suspend fun add(item: T): T
    suspend fun update(t: T): T
    suspend fun delete(id: ID): Boolean
}

//use for storage data need account id
interface AuthDataRepository <T, ID> {
    suspend fun findAll(accountId: String): List<T>
    suspend fun findById(id: ID, accountId: String): T?
    suspend fun add(item: T, accountId: String): T
    suspend fun update(item: T, accountId: String): T
    suspend fun delete(id: ID, accountId: String): Boolean
}