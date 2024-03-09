package com.vinhnt_study.db

import com.vinhnt_study.models.AuthData
import com.vinhnt_study.models.Category
import com.vinhnt_study.models.MoneySource
import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

object Moneys : Table(){
    val id = uuid("id")
    val accountId = reference("account_id", Accounts.id)
    val amount = double("amount")
    val type = integer("type")
    val category = reference("category_id", Categories.id)
    val description = varchar("description", 500)
    val sourceId = reference("source_id", MoneySources.id)
    val date = datetime("date")
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}

object MoneySources : Table("money_sources") {
    val id = uuid("id")
    val accountId = reference("account_id", Accounts.id)
    val name = varchar("name", 100)
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}

object Categories : Table() {
    val id = uuid("id")
    val accountId = reference("account_id", Accounts.id)
    val type = integer("type")
    val name = varchar("name", 100)
    val createDate = datetime("create_date")
    val updateDate = datetime("update_date")

    override val primaryKey = PrimaryKey(id)
}

//create interface Category DAO
//interface CRUDCategoryDAO : CRUDDAO<Category, UUID>


//create class Category DAO implementation

//create money source dao
interface CRUDMoneySourceDAO : CRUDDAO<AuthData<MoneySource>, MoneySource, UUID> {
    suspend fun findAll(accountId: UUID): List<MoneySource>
}

//create class money source dao implementation
class MoneySourceDAOImpl : CRUDMoneySourceDAO {
    private fun resultRowToMoneySource(row: ResultRow) = MoneySource(
        id = row[MoneySources.id],
        name = row[MoneySources.name],
    )

    override suspend fun create(item: AuthData<MoneySource>): MoneySource = DatabaseSingleton.dbQuery {
        val source = item.data

        val insertStatement = MoneySources.insert {
            it[id] = UUID.randomUUID()
            it[name] = source.name
            it[createDate] = LocalDateTime.now()
            it[updateDate] = LocalDateTime.now()
            it[accountId] = UUID.fromString(item.accountId)
        }
        insertStatement.resultedValues!!.singleOrNull()!!.let(::resultRowToMoneySource)
    }

    override suspend fun read(id: UUID): MoneySource? = DatabaseSingleton.dbQuery {
        MoneySources
            .select { MoneySources.id eq id }
            .map { resultRowToMoneySource(it) }
            .singleOrNull()
    }

    override suspend fun update(item: AuthData<MoneySource>): MoneySource = DatabaseSingleton.dbQuery {
        val moneySourceId = item.data.id
        val accountId = UUID.fromString(item.accountId)

        val updatedRows = MoneySources.update(
            where = { (MoneySources.id eq moneySourceId) and (MoneySources.accountId eq accountId) }
        ) {
            it[name] = item.data.name
            it[updateDate] = LocalDateTime.now()
        }

        if (updatedRows > 0) {
            val updatedMoneySource = read(item.data.id) ?: throw IllegalStateException("Money source not found")
            updatedMoneySource
        } else {
            throw IllegalStateException("Money source not found")
        }
    }

    override suspend fun delete(id: UUID): Boolean = DatabaseSingleton.dbQuery {
        MoneySources.deleteWhere { MoneySources.id eq id } > 0
    }

    override suspend fun findAll(accountId: UUID): List<MoneySource> = DatabaseSingleton.dbQuery {
        MoneySources
            .select { MoneySources.accountId eq accountId }
            .map { resultRowToMoneySource(it) }
    }
}