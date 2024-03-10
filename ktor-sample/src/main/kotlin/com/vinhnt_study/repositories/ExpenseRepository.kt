package com.vinhnt_study.repositories

import com.vinhnt_study.models.Money
import com.vinhnt_study.models.MoneyRequest


class ExpenseRepository : DataRepository<Money, MoneyRequest> {
    override suspend fun findAll(): List<Money> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: MoneyRequest): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun update(t: Money): Money {
        TODO("Not yet implemented")
    }

    override suspend fun add(item: Money): Money {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: MoneyRequest): Money? {
        TODO("Not yet implemented")
    }

}

