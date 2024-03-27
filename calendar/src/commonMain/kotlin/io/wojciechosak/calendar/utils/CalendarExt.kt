package io.wojciechosak.calendar.utils

import io.wojciechosak.calendar.config.MonthYear
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Checks if a given year is a leap year.
 *
 * @param year The year to check for leap year.
 * @return true if the year is a leap year, false otherwise.
 */
fun isLeapYear(year: Int): Boolean = try {
    LocalDate(year, 2, 29)
    true
} catch (exception: IllegalArgumentException) {
    false
}

/**
 * Calculates the length (number of days) of a given month in a specified year.
 *
 * @param month The month for which to determine the length.
 * @param year The year in which the month occurs.
 * @return The length of the month in days.
 */
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

internal fun LocalDateTime.toLocalDate(): LocalDate = LocalDate(year, month, dayOfMonth)

fun LocalDate.copy(
    year: Int = this.year,
    month: Month = this.month,
    day: Int = this.dayOfMonth,
): LocalDate = try {
    LocalDate(year, month, day)
} catch (e: IllegalArgumentException) {
    LocalDate(year, month, monthLength(month, year))
}

/**
 * Returns the current date.
 *
 * @return The current date.
 */
fun LocalDate.Companion.today(): LocalDate = Clock.System.now()
    .toLocalDateTime(TimeZone.currentSystemDefault())
    .toLocalDate()

fun LocalDate.toMonthYear(): MonthYear = MonthYear(this.month, this.year)

fun LocalDate.daySimpleName() = dayOfWeek.name.substring(IntRange(0, 2))
