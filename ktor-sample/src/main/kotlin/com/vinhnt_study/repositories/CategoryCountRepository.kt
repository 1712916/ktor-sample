package com.vinhnt_study.repositories

import com.vinhnt_study.db.Categories
import com.vinhnt_study.db.DatabaseSingleton
import com.vinhnt_study.db.Moneys
import com.vinhnt_study.models.CategoryCount
import com.vinhnt_study.utils.toUUID
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.count
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select

interface CategoryCountRepository {
    suspend fun countAll(account: String): List<CategoryCount>
    suspend fun countById(account: String, id: String): CategoryCount
    suspend fun countByIds(account: String, ids: List<String>): List<CategoryCount>
}

class CategoryCountRepositoryImpl : CategoryCountRepository {
    override suspend fun countAll(account: String): List<CategoryCount> = DatabaseSingleton.dbQuery {
        Categories.leftJoin(Moneys)
            .slice(Categories.id, Categories.name, Moneys.id.count())
            .select { (Moneys.accountId eq account.toUUID()) or
                    (Moneys.accountId.isNull()) and
                    (Categories.accountId eq Moneys.accountId) }
            .groupBy(Categories.id, Categories.name)
            .map { row ->
                CategoryCount(
                    id = row[Categories.id].toString(),
                    name = row[Categories.name],
                    count = row[Moneys.id.count()]
                )
            }
    }

    override suspend fun countById(account: String, id: String): CategoryCount = DatabaseSingleton.dbQuery {
        Categories.leftJoin(Moneys)
            .slice(Categories.id, Categories.name, Moneys.id.count())
            .select {
                (Moneys.accountId eq account.toUUID()) or
                        (Moneys.accountId.isNull()) and
                        (Categories.accountId eq Moneys.accountId) and
                        (Categories.id eq id.toUUID())
            }
            .groupBy(Categories.id, Categories.name)
            .single().let { row ->
                CategoryCount(
                    id = row[Categories.id].toString(),
                    name = row[Categories.name],
                    count = row[Moneys.id.count()],
                )
            }
    }

    override suspend fun countByIds(account: String, ids: List<String>): List<CategoryCount> =
        DatabaseSingleton.dbQuery {
            Categories.leftJoin(Moneys)
                .slice(Categories.id, Categories.name, Moneys.id.count())
                .select { (Moneys.accountId eq account.toUUID()) or
                        (Moneys.accountId.isNull()) and
                        (Categories.accountId eq Moneys.accountId) and
                        (Categories.id inList ids.map { it.toUUID() }) }
                .groupBy(Categories.id, Categories.name)
                .map { row ->
                    CategoryCount(
                        id = row[Categories.id].toString(),
                        name = row[Categories.name],
                        count = row[Moneys.id.count()]
                    )
                }
        }

}