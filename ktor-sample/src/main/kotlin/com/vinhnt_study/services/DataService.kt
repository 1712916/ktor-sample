package com.vinhnt_study.services


//create a data service interface
interface DataService<T, R> {
    suspend fun findAll(): List<T>
    suspend fun findById(id: String): T?
    suspend fun add(item: R): T
    suspend fun update(t: T): T
    suspend fun delete(id: String): T
}
