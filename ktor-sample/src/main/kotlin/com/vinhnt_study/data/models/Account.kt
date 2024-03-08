package com.vinhnt_study.data.models

import com.vinhnt_study.db.DatabaseSingleton.dbQuery
import com.vinhnt_study.utils.LocaleDateTimeSerializer
import com.vinhnt_study.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
class Account(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val account: String,
    val password: String,
    val email: String,
    @Serializable(with = LocaleDateTimeSerializer::class)
    val createDate: LocalDateTime,
    @Serializable(with = LocaleDateTimeSerializer::class)
    val updateDate: LocalDateTime,
)


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
interface CRUDAccountDAO {
    suspend fun create(account: Account): Account
    suspend fun read(id: UUID): Account?
    suspend fun update(account: Account): Account
    suspend fun delete(id: UUID): Boolean
}

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
    override suspend fun create(account: Account): Account = dbQuery {
        val insertStatement = Accounts.insert {
            it[Accounts.id] = UUID.randomUUID()
            it[Accounts.account] = account.account
            it[Accounts.password] = account.password
            it[Accounts.email] = account.email
            it[Accounts.createDate] = account.createDate
            it[Accounts.updateDate] = account.updateDate
        }
      insertStatement.resultedValues!!.singleOrNull()!!.let(::resultRowToAccount)
    }

    override suspend fun read(id: UUID): Account? = dbQuery {
       //read account by id
         Accounts
            .select { Accounts.id eq id }
            .map(::resultRowToAccount)
            .singleOrNull()
    }

    override suspend fun update(account: Account): Account = dbQuery {
        val updateStatement = Accounts.update({ Accounts.id eq account.id }) {
            it[Accounts.password] = account.password
            it[Accounts.email] = account.email
            it[Accounts.updateDate] = LocalDateTime.now()
        } > 0
        read(account.id) ?: throw IllegalStateException("Account not found")
    }

    override suspend fun delete(id: UUID): Boolean  = dbQuery {
       //delete account by id
      Accounts.deleteWhere { Accounts.id eq id } > 0
    }

    //find account by email
    override suspend fun findByEmail(email: String): Account? = dbQuery {
        Accounts
            .select { Accounts.email eq email }
            .map(::resultRowToAccount)
            .singleOrNull()
    }

    //find account by account
    override suspend fun findByAccount(account: String): Account? = dbQuery {
        Accounts
            .select { Accounts.account eq account }
            .map(::resultRowToAccount)
            .singleOrNull()
    }
}
