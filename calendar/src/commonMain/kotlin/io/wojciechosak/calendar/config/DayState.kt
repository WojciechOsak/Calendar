package io.wojciechosak.calendar.config

import kotlinx.datetime.LocalDate

data class DayState(
    val date: LocalDate,
    val isActiveDay: Boolean = false,
    val isForPreviousMonth: Boolean = false,
    val isForNextMonth: Boolean = false,
    val enabled: Boolean = true,
)
