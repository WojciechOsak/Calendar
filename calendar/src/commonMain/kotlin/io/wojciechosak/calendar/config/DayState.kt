package io.wojciechosak.calendar.config

import kotlinx.datetime.LocalDate

/**
 * Data class representing the state of a day in a calendar.
 *
 * @property date The date associated with this day state.
 * @property isActiveDay Whether the day is active or not. Default is false.
 * @property isForPreviousMonth Whether the day belongs to the previous month. Default is false.
 * @property isForNextMonth Whether the day belongs to the next month. Default is false.
 * @property enabled Whether the day is enabled(clickable) or disabled. Default is true.
 */
data class DayState(
    val date: LocalDate,
    val isActiveDay: Boolean = false,
    val isForPreviousMonth: Boolean = false,
    val isForNextMonth: Boolean = false,
    val enabled: Boolean = true,
)
