package com.vinhnt_study.data.repositories

abstract  class DataRepository<T, R> {
    abstract  fun findAll(): List<T>
    abstract  fun findById(id: String): T?
    abstract  fun add(t: R): T
    abstract  fun update(t: T): T
    abstract  fun delete(id: String): T
}