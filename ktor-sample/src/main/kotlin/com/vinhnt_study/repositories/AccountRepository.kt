package com.vinhnt_study.repositories

import com.vinhnt_study.models.authentication.RegisterRequest
import com.vinhnt_study.db.AccountDAO
import com.vinhnt_study.db.AccountDAOImpl
import com.vinhnt_study.models.Account
import java.time.LocalDateTime
import java.util.*


//create interface AccountRepository
interface AccountRepository : DataRepository<Account, RegisterRequest> {
    suspend fun findByAccount(account: String): Account?
    suspend fun findByEmail(email: String): Account?
}

class AccountRepositoryImpl : AccountRepository {
    private val dao: AccountDAO = AccountDAOImpl()
    //find account by account
    override suspend fun findByAccount(account: String): Account? {
        return dao.findByAccount(account)
    }

    //fund account by email
    override suspend fun findByEmail(email: String): Account? {
        return dao.findByEmail(email)
    }
    override suspend fun findAll(): List<Account> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: String): Account? {
        return  dao.read(UUID.fromString(id))
    }

    override suspend fun delete(id: String): Account {
        val account = dao.read(UUID.fromString(id)) ?: throw IllegalArgumentException("No account found for id $id.")

        return dao.delete(UUID.fromString(id)).let { if (it) account else throw IllegalArgumentException("Failed to delete account for id $id.")}
    }

    override suspend fun update(t: Account): Account {
       return  dao.update(t)
    }

    override suspend fun add(t: RegisterRequest): Account {
        return dao.create(
            Account(
                UUID.randomUUID(),
                t.account,
                t.password,
                t.email,
                LocalDateTime.now(),
                LocalDateTime.now(),
            )
        )
    }

}

