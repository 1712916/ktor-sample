package com.vinhnt_study.repositories

import com.vinhnt_study.db.Accounts
import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.models.Account
import com.vinhnt_study.utils.toUUID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import java.util.*


//create interface AccountRepository
interface AccountRepository : DataRepository<Account, String> {
    suspend fun findByAccount(account: String): Account?
    suspend fun findByEmail(email: String): Account?
}

class AccountRepositoryImpl : AccountRepository {
    private fun resultRowToAccount(row: ResultRow) = Account(
        id = row[Accounts.id].toString(),
        account = row[Accounts.account],
        password = row[Accounts.password],
        email = row[Accounts.email],
        createDate = row[Accounts.createDate],
        updateDate = row[Accounts.updateDate]
    )

    //find account by account
    override suspend fun findByAccount(account: String): Account? = DatabaseSingleton.dbQuery {
        Accounts
            .select { Accounts.account eq account }
            .map(::resultRowToAccount)
            .singleOrNull()
    }

    //fund account by email
    override suspend fun findByEmail(email: String): Account? = DatabaseSingleton.dbQuery {
        Accounts
            .select { Accounts.email eq email }
            .map(::resultRowToAccount)
            .singleOrNull()
    }
    override suspend fun findAll(): List<Account> = DatabaseSingleton.dbQuery {
        Accounts
            .selectAll()
            .map(::resultRowToAccount)
            .toList()
    }

    override suspend fun findById(id: String): Account? = DatabaseSingleton.dbQuery {
        //read account by id
        Accounts
            .select { Accounts.id eq id.toUUID() }
            .map(::resultRowToAccount)
            .singleOrNull()
    }

    override suspend fun delete(id: String): Boolean = DatabaseSingleton.dbQuery {
        //delete account by id
        Accounts.deleteWhere { Accounts.id eq UUID.fromString(id) } > 0
    }

    override suspend fun update(t: Account): Account = DatabaseSingleton.dbQuery {
        val updateStatement = Accounts.update({ Accounts.id eq t.id!!.toUUID() }) {
            it[password] = t.password
            it[email] = t.email
            it[updateDate] = LocalDateTime.now()
        } > 0

        if (updateStatement) Accounts
            .select { Accounts.id eq t.id!!.toUUID() }
            .map(::resultRowToAccount)
            .single() else throw IllegalStateException("Account not found")
    }


    override suspend fun add(item: Account): Account = DatabaseSingleton.dbQuery {
       val now = LocalDateTime.now()
        val insertStatement = Accounts.insert {
            it[id] = UUID.randomUUID()
            it[account] = item.account
            it[password] = item.password
            it[email] = item.email
            it[createDate] = now
            it[updateDate] = now
        }
        insertStatement.resultedValues!!.singleOrNull()!!.let(::resultRowToAccount)
    }
}

