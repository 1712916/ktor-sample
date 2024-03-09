package com.vinhnt_study.db

interface CRUDDAO<INPUT, OUTPUT, ID> {
    suspend fun create(item: INPUT): OUTPUT
    suspend fun read(id: ID): OUTPUT?
    suspend fun update(item: INPUT): OUTPUT
    suspend fun delete(id: ID): Boolean
}