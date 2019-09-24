package com.jbr.utils

import java.util.*

fun Calendar.date(year: Int, month: Int, day: Int): Date {
    set(year, month - 1, day, 0, 0)
    return time
}