package com.vinhnt_study.services


//create a data service interface
interface DataService<T, R> {
    fun findAll(): List<T>
    fun findById(id: String): T?
    fun add(item: R): T
    fun update(t: T): T
    fun delete(id: String): T
}
