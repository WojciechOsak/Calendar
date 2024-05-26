package io.wojciechosak.calendar.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.wojciechosak.calendar.utils.toMonthYear
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

/**
 * Data class representing the configuration of a calendar.
 *
 * @property minDate The minimum date selectable in the calendar.
 * @property maxDate The maximum date selectable in the calendar.
 * @property monthYear The month and year currently displayed in the calendar.
 * @property dayOfWeekOffset The offset to align the start of the week.
 * @property showNextMonthDays Whether to show days from the next month in the calendar.
 * @property showPreviousMonthDays Whether to show days from the previous month in the calendar.
 * @property showHeader Whether to show the header displaying the month and year.
 * @property showWeekdays Whether to show the weekdays header.
 * @property selectedDates The list of selected dates in the calendar.
 */
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

/**
 * Composable function to remember the state of a calendar.
 *
 * @param startDate The initial date of the calendar.
 * @param minDate The minimum date selectable in the calendar. Default is January 1, 1971.
 * @param maxDate The maximum date selectable in the calendar. Default is 15 years from the start date.
 * @param monthOffset The offset in months from the start date to determine the initial visible month.
 * @param dayOfWeekOffset The offset to align the start of the week. Default is 0 (Monday).
 * @param showNextMonthDays Whether to show days from the next month in the calendar. Default is true.
 * @param showPreviousMonthDays Whether to show days from the previous month in the calendar. Default is true.
 * @param showHeader Whether to show the header displaying the month and year. Default is true.
 * @param showWeekdays Whether to show the weekdays header. Default is true.
 * @param selectedDates The list of selected dates in the calendar. Default is an empty mutable list.
 * @return A mutable state holding the configuration of the calendar.
 */
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
): MutableState<CalendarConfig> = remember {
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

fun MutableState<CalendarConfig>.nextMonth() {
	val currentConfig = value
	value = value.copy(
		monthYear = currentConfig.monthYear.toLocalDate().plus(1, DateTimeUnit.MONTH).toMonthYear()
	)
}

fun MutableState<CalendarConfig>.previousMonth() {
	val currentConfig = value
	value = value.copy(
		monthYear = currentConfig.monthYear.toLocalDate().minus(1, DateTimeUnit.MONTH).toMonthYear()
	)
}