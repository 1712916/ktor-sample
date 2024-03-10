package com.vinhnt_study.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

fun Date.toLocalDateTime(): LocalDateTime {
    val instant = this.toInstant()
    val zoneId = ZoneId.systemDefault() // Or specify the desired time zone
    return LocalDateTime.ofInstant(instant, zoneId)
}