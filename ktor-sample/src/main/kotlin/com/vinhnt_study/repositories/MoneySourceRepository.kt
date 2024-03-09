package com.vinhnt_study.repositories

import com.vinhnt_study.db.CRUDMoneySourceDAO
import com.vinhnt_study.db.MoneySourceDAOImpl
import com.vinhnt_study.models.AuthData
import com.vinhnt_study.models.MoneySource
import java.util.UUID
//route -> service -> repository
//
//route: handle request, call service
//service: handle business logic, call repository
//repository: handle data, call database
//dao : data access object, call database

//CREATE interface findByAccount

data class AccountIdData<T> (val accountId: UUID, val data: T)
interface FindByAccountId<T> {
    suspend fun findByAccountId(id: UUID): T?
}

interface  MoneySourceRepository : AuthDataRepository<MoneySource, MoneySource>
class MoneySourceRepositoryImpl : MoneySourceRepository {

    val moneySourceDAO: CRUDMoneySourceDAO = MoneySourceDAOImpl()
    override suspend fun findAll(accountId: UUID): List<MoneySource> {
        return moneySourceDAO.findAll(accountId)
    }

    override suspend fun findById(id: String, accountId: UUID): MoneySource? {
       return moneySourceDAO.read(UUID.fromString(id))
    }

    override suspend fun delete(id: String, accountId: UUID): MoneySource {
        //find by id and account id if not null delete it
        val moneySource = moneySourceDAO.read(UUID.fromString(id)).let {
            it ?: throw Exception("Money source not found")
        }

          moneySourceDAO.delete(UUID.fromString(id)).let {
              if (it  ) {
                  return moneySource
              } else {
                  throw Exception("Money source not found")
              }
          }
    }

    override suspend fun update(t: MoneySource, accountId: UUID): MoneySource {
       return moneySourceDAO.update(AuthData(accountId.toString(), t))
    }

    override suspend fun add(t: MoneySource, accountId: UUID): MoneySource {
       return  moneySourceDAO.create(AuthData(accountId.toString(), t))
    }
}