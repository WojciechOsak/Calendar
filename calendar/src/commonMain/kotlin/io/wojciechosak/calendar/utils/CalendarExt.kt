package io.wojciechosak.calendar.utils

import io.wojciechosak.calendar.config.MonthYear
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun isLeapYear(year: Int): Boolean {
    return try {
        LocalDate(year, 2, 29)
        true
    } catch (exception: IllegalArgumentException) {
        false
    }
}

fun monthLength(
    month: Month,
    year: Int,
): Int {
    val isLeapYear = isLeapYear(year)
    return when (month) {
        Month.FEBRUARY -> if (isLeapYear) 29 else 28
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        else -> 31
    }
}

internal fun LocalDateTime.toLocalDate(): LocalDate {
    return LocalDate(year, month, dayOfMonth)
}

fun LocalDate.copy(
    year: Int = this.year,
    month: Month = this.month,
    day: Int = this.dayOfMonth,
): LocalDate {
    return try {
        LocalDate(year, month, day)
    } catch (e: IllegalArgumentException) {
        LocalDate(year, month, monthLength(month, year))
    }
}

fun LocalDate.Companion.today(): LocalDate {
    return Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .toLocalDate()
}

fun LocalDate.toMonthYear(): MonthYear {
    return MonthYear(this.month, this.year)
}

fun LocalDate.daySimpleName() = dayOfWeek.name.substring(IntRange(0, 2))
