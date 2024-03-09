package com.vinhnt_study.db

import com.vinhnt_study.models.Account
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object Accounts : Table() {
    val id = uuid("id")
    val account = varchar("account", 128)
    val password = varchar("password", 128)
    val email = varchar("email", 128)
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}

//create interface account DAO
interface CRUDAccountDAO : CRUDDAO<Account, Account, UUID>
interface SearchAccountDAO {
    suspend fun findByEmail(email: String): Account?
    suspend fun findByAccount(account: String): Account?
}

interface AccountDAO : CRUDAccountDAO, SearchAccountDAO

//create class account DAO implementation
class AccountDAOImpl : AccountDAO {
    private fun resultRowToAccount(row: ResultRow) = Account(
        id = row[Accounts.id],
        account = row[Accounts.account],
        password = row[Accounts.password],
        email = row[Accounts.email],
        createDate = row[Accounts.createDate],
        updateDate = row[Accounts.updateDate]
    )
    override suspend fun create(item: Account): Account = DatabaseSingleton.dbQuery {
        val insertStatement = Accounts.insert {
            it[id] = UUID.randomUUID()
            it[account] = item.account
            it[password] = item.password
            it[email] = item.email
            it[createDate] = item.createDate
            it[updateDate] = item.updateDate
        }
        insertStatement.resultedValues!!.singleOrNull()!!.let(::resultRowToAccount)
    }

    override suspend fun read(id: UUID): Account? = DatabaseSingleton.dbQuery {
        //read account by id
        Accounts
            .select { Accounts.id eq id }
            .map(::resultRowToAccount)
            .singleOrNull()
    }

    override suspend fun update(item: Account): Account = DatabaseSingleton.dbQuery {
        val updateStatement = Accounts.update({ Accounts.id eq item.id }) {
            it[password] = item.password
            it[email] = item.email
            it[updateDate] = LocalDateTime.now()
        } > 0
        read(item.id) ?: throw IllegalStateException("Account not found")
    }

    override suspend fun delete(id: UUID): Boolean  = DatabaseSingleton.dbQuery {
        //delete account by id
        Accounts.deleteWhere { Accounts.id eq id } > 0
    }

    //find account by email
    override suspend fun findByEmail(email: String): Account? = DatabaseSingleton.dbQuery {
        Accounts
            .select { Accounts.email eq email }
            .map(::resultRowToAccount)
            .singleOrNull()
    }

    //find account by account
    override suspend fun findByAccount(account: String): Account? = DatabaseSingleton.dbQuery {
        Accounts
            .select { Accounts.account eq account }
            .map(::resultRowToAccount)
            .singleOrNull()
    }
}
