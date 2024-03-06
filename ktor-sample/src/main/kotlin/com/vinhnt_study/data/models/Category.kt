package com.vinhnt_study.data.models

import com.vinhnt_study.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
open  class Category(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String
)

class ExpenseCategory(
     id: UUID,
    name: String,
) : Category(id, name)


class IncomeCategory (
    id: UUID,
    name: String,
) : Category(id, name)
