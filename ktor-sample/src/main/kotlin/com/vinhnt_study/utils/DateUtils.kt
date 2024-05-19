package com.vinhnt_study.utils

import io.ktor.server.util.*
import java.util.Date

object DateUtils {
    val commonDateFormat : String = "yyyy/MM/dd"
    private fun getQuarterRanges(year: Int) : List<Pair<String, String>> {
        return listOf(Pair("${year}/01/01", "${year}/03/31"), Pair("${year}/04/01", "${year}/06/30"), Pair("${year}/07/01", "${year}/09/30"), Pair("${year}/10/01", "${year}/12/31"))
    }

    fun getQuarterDateRanges( year: Int) : List<Pair<Date, Date>> {
        return  getQuarterRanges(year).map {
           Pair(it.first.toDate(commonDateFormat), it.second.toDate(commonDateFormat))
        }
    }

    fun getQuarterRange(year: Int, quarter: Int) : Pair<Date, Date> {
        assert(quarter in 1..4)

        return getQuarterDateRanges(year)[quarter - 1]
    }

    fun getStartAndEndDateOfMonth(month: Int, year: Int) : Pair<Date, Date> {
        val startDate = "${year}/${month}/01".toDate(commonDateFormat)
        val endDate = startDate.toLocalDate().plusMonths(1).minusDays(1).toDate()
        return Pair(startDate, endDate)
    }
}