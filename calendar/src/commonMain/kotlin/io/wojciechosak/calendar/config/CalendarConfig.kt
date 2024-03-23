package io.wojciechosak.calendar.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.wojciechosak.calendar.utils.toMonthYear
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

@Stable
data class CalendarConfig(
    val minDate: LocalDate,
    val maxDate: LocalDate,
    val monthYear: MonthYear,
    val dayOfWeekOffset: Int,
    val showNextMonthDays: Boolean,
    val showPreviousMonthDays: Boolean,
    val showHeader: Boolean,
    val showWeekdays: Boolean,
    val selectedDates: List<LocalDate>,
)

@Composable
fun rememberCalendarState(
    startDate: LocalDate,
    minDate: LocalDate = LocalDate(1971, 1, 1),
    maxDate: LocalDate = startDate.plus(15, DateTimeUnit.YEAR),
    monthOffset: Int,
    dayOfWeekOffset: Int = 0,
    showNextMonthDays: Boolean = true,
    showPreviousMonthDays: Boolean = true,
    showHeader: Boolean = true,
    showWeekdays: Boolean = true,
    selectedDates: MutableList<LocalDate> = mutableListOf(),
): MutableState<CalendarConfig> {
    return remember {
        mutableStateOf(
            CalendarConfig(
                minDate = minDate,
                maxDate = maxDate,
                monthYear = startDate.plus(monthOffset, DateTimeUnit.MONTH).toMonthYear(),
                dayOfWeekOffset = dayOfWeekOffset,
                showNextMonthDays = showNextMonthDays,
                showPreviousMonthDays = showPreviousMonthDays,
                showHeader = showHeader,
                showWeekdays = showWeekdays,
                selectedDates = selectedDates,
            ),
        )
    }
}
