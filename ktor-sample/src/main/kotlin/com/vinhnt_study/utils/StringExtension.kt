package com.vinhnt_study.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}

//string to date
fun String.toDate(pattern: String = "yyyy-MM-dd"): Date {
    val dateFormat = SimpleDateFormat(pattern)
    return dateFormat.parse(this)
}

