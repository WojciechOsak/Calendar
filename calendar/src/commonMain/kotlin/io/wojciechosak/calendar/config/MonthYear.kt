package io.wojciechosak.calendar.config

import io.wojciechosak.calendar.utils.toMonthYear
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.plus

data class MonthYear(val month: Month, val year: Int)

fun MonthYear.toLocalDate() = LocalDate(year, month, 1)

fun MonthYear.monthOffset(monthOffset: Int) =
    this
        .toLocalDate()
        .plus(monthOffset, DateTimeUnit.MONTH)
        .toMonthYear()
