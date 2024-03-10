package com.vinhnt_study.repositories

 import com.vinhnt_study.db.Categories
import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.models.Category
import com.vinhnt_study.models.MoneyType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import java.util.*

//interface category repository
interface  CategoryRepository : AuthDataRepository<Category, String>

//category repository implementation
class CategoryRepositoryImpl : CategoryRepository {
   //create category dao
   private fun resultRowToCategory(row: ResultRow) = Category(
       id = row[Categories.id].toString(),
       name = row[Categories.name],
       type = MoneyType.entries[row[Categories.type]],
   )


    override suspend fun findAll(accountId: String): List<Category>  = DatabaseSingleton.dbQuery{
        Categories
            .select { Categories.accountId eq UUID.fromString(accountId) }
            .map { resultRowToCategory(it) }
    }

    override suspend fun findById(id: String, accountId: String): Category?  = DatabaseSingleton.dbQuery{
        Categories
            .select { Categories.id eq UUID.fromString(id) and (Categories.accountId eq UUID.fromString(accountId))}
            .map { resultRowToCategory(it) }
            .singleOrNull()
    }

    override suspend fun add(item: Category, accountId: String): Category = DatabaseSingleton.dbQuery  {
        val insertStatement = Categories.insert {
            it[id] = UUID.randomUUID()
            it[name] = item.name
            it[type] = item.type.ordinal
            it[createDate] = LocalDateTime.now()
            it[updateDate] = LocalDateTime.now()
            it[Categories.accountId] = UUID.fromString(accountId)
        }
        insertStatement.resultedValues!!.singleOrNull()!!.let(::resultRowToCategory)
    }

    override suspend fun update(item: Category, accountId: String): Category  = DatabaseSingleton.dbQuery {
        val categoryId = UUID.fromString(item.id)
        val accountUUID = UUID.fromString(accountId)

        val updatedRows = Categories.update(
            where = { (Categories.id eq categoryId) and (Categories.accountId eq accountUUID) }
        ) {
            it[name] = item.name
            it[type] = item.type.ordinal
            it[updateDate] = LocalDateTime.now()
        }

        if (updatedRows > 0) {
            Categories
                .select { Categories.id eq categoryId }
                .map { resultRowToCategory(it) }
                .single()
        } else {
            throw IllegalStateException("Category not found")
        }
    }

    override suspend fun delete(id: String, accountId: String): Boolean = DatabaseSingleton.dbQuery {
        Categories.deleteWhere { Categories.id eq UUID.fromString(id) } > 0
    }
}
