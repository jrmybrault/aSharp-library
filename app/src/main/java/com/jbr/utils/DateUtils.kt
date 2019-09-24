package com.jbr.utils

import java.util.*
import java.util.Calendar.*

fun Calendar.dateFrom(year: Int, month: Int, day: Int): Date {
    set(year, month - 1, day, 0, 0)
    return time
}

fun numberOfYearsBetween(first: Date, last: Date): Int {
    val firstCalendar = getInstance(Locale.getDefault()).apply { time = first }
    val lastCalendar = getInstance(Locale.getDefault()).apply { time = last }

    var yearsDelta = lastCalendar.get(YEAR) - firstCalendar.get(YEAR)

    if (firstCalendar.get(MONTH) > lastCalendar.get(MONTH) ||
        firstCalendar.get(MONTH) == lastCalendar.get(MONTH) && firstCalendar.get(DATE) > lastCalendar.get(DATE)
    ) {
        yearsDelta--
    }

    return yearsDelta
}