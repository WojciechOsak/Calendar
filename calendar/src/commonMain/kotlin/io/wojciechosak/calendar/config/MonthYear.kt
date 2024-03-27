package io.wojciechosak.calendar.config

import io.wojciechosak.calendar.utils.toMonthYear
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.plus

/**
 * Data class representing a month and year combination.
 *
 * @property month The month component of the month-year combination.
 * @property year The year component of the month-year combination.
 */
data class MonthYear(val month: Month, val year: Int)

fun MonthYear.toLocalDate() = LocalDate(year, month, 1)

fun MonthYear.monthOffset(monthOffset: Int) = this
    .toLocalDate()
    .plus(monthOffset, DateTimeUnit.MONTH)
    .toMonthYear()
