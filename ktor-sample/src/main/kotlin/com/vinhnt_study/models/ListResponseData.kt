package com.vinhnt_study.models

import kotlinx.serialization.Serializable

@Serializable
data class ListResultData<T>(
    val list: List<T>,
    val page: Long,
    val pageSize: Int,
    val total: Long
)