package com.vinhnt_study.db

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere

interface CrudDAO<T, ID> {
    suspend fun create(item: T): T
    suspend fun read(id: ID): T?
    suspend fun update(item: T): T
    suspend fun delete(id: ID): Boolean
}
