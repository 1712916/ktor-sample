package com.vinhnt_study.repositories

import java.util.UUID

interface DataRepository<T, R> {
    suspend fun findAll(): List<T>
    suspend fun findById(id: String): T?
    suspend fun add(t: R): T
    suspend fun update(t: T): T
    suspend fun delete(id: String): T
}

//use for storage data need account id
interface AuthDataRepository <T, R> {
    suspend fun findAll(accountId: UUID): List<T>
    suspend fun findById(id: String, accountId: UUID): T?
    suspend fun add(t: R, accountId: UUID): T
    suspend fun update(t: T, accountId: UUID): T
    suspend fun delete(id: String, accountId: UUID): T
}