package com.vinhnt_study.models

import java.net.URI

data class SortCriteria(val field: String, val order: String)

open class Query(
    val query: String = "",
    val page: Int = DEFAULT_PAGE,
    val pageSize: Int = DEFAULT_PAGE_SIZE,
    val sortCriteria: List<SortCriteria> = emptyList()
) {
    init {
        // Validate and sanitize inputs
        if (page < 0)  {
            throw IllegalArgumentException("Page start from 0")
        }
        if (pageSize < 1 || pageSize > 100) {
            throw IllegalArgumentException("Page size limits from 1 to 100")
        }
        sortCriteria.forEach {
            if (it.order !in listOf("asc", "desc")) {
                throw IllegalArgumentException("Sort order must be 'asc' or 'desc'")
            }
        }
    }

    companion object {

        const val DEFAULT_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 10
        const val QUERY_KEY = "query"
        const val LIMIT_KEY = "page_size"
        const val OFFSET_KEY = "page"
        const val SORT_KEY = "sort"

        fun fromUrl(url: String): Query {
            val uri = URI(url)
            val queryParams = uri.query.split("&").associate {
                val (key, value) = it.split("=")
                key to value
            }

            val query = queryParams[QUERY_KEY] ?: ""
            val page = queryParams[LIMIT_KEY]?.toIntOrNull() ?: DEFAULT_PAGE
            val pageSize = queryParams[OFFSET_KEY]?.toIntOrNull() ?: DEFAULT_PAGE_SIZE

            val sortCriteria = queryParams.filter { it.key == SORT_KEY }.map {
                val (field, order) = it.value.split(",")
                SortCriteria(field, order)
            }

            return Query(query, page, pageSize, sortCriteria)
        }
    }

    fun toUrl(baseUrl: String, endpoint: String): String {
        val queryParams = mutableListOf<Pair<String, String>>()
        queryParams.add(QUERY_KEY to query)
        queryParams.add(OFFSET_KEY to page.toString())
        queryParams.add(LIMIT_KEY to pageSize.toString())

        sortCriteria.forEach {
            queryParams.add(SORT_KEY to "${it.field},${it.order}")
        }

        val queryString = queryParams.joinToString("&") { "${it.first}=${it.second}" }
        return "$baseUrl$endpoint?$queryString"
    }
}
