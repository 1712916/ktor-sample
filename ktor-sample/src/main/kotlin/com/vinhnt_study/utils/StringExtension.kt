package com.vinhnt_study.utils

import java.util.*

fun String.toUUID(): UUID {
    return UUID.fromString(this)
}