package com.vinhnt_study.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

fun Date.toLocalDateTime(): LocalDateTime {
    val instant = this.toInstant()
    val zoneId = ZoneId.systemDefault() // Or specify the desired time zone
    return LocalDateTime.ofInstant(instant, zoneId)
}

fun Date.toLocalDate(): LocalDate {
    val instant = this.toInstant()
    val zoneId = ZoneId.systemDefault() // Or specify the desired time zone
    return LocalDate.ofInstant(instant, zoneId)
}

fun LocalDateTime.toDate() : Date {
    return Date.from(atZone(ZoneId.systemDefault()).toInstant())
}

fun LocalDate.toDate() : Date {
    return Date.from(atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun getAllDaysBetweenDates(startDate: Date, endDate: Date): List<Date> {
    val days = mutableListOf<Date>()
    var currentDate = startDate.toLocalDate()
    val endLocalDate = endDate.toLocalDate()

    while (!currentDate.isAfter(endLocalDate)) {
        days.add(currentDate.toDate())
        currentDate = currentDate.plusDays(1)
    }

    return days
}

fun sortDate(from:Date, to: Date) : Pair<Date, Date> {
    if (from.after(to)) {
        return  Pair(to, from)
    }
    return  Pair(from, to )
}

fun  main() {
    val d = Date()
    println("date: ${d}")
    println("toLocaleDateTime: ${d.toLocalDateTime()}")
    println("toDate: ${d.toLocalDateTime().toDate()}")
}

