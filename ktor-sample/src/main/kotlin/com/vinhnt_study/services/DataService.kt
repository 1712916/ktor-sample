package com.vinhnt_study.services


//create a data service interface
interface DataService<T, R> {
    suspend fun findAll(): List<T>
    suspend fun findById(id: String): T?
    suspend fun add(item: R): T
    suspend fun update(item: T): T
    suspend fun delete(id: String): Boolean
}


//create a data service interface for account id
interface AuthDataService<T, R> {
    suspend fun findAll(accountId: String): List<T>
    suspend fun findById(id: String, accountId: String): T?
    suspend fun add(item: T, accountId: String): T
    suspend fun update(t: T, accountId: String): T
    suspend fun delete(id: String, accountId: String): Boolean
}