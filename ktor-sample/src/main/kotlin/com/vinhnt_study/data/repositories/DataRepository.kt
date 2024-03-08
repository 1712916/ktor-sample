package com.vinhnt_study.data.repositories

interface DataRepository<T, R> {
    suspend fun findAll(): List<T>
    suspend fun findById(id: String): T?
    suspend fun add(t: R): T
    suspend fun update(t: T): T
    suspend fun delete(id: String): T
}