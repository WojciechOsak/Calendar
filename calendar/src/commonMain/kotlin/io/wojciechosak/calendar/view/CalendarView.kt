package io.wojciechosak.calendar.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.config.CalendarConfig
import io.wojciechosak.calendar.config.DayState
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.copy
import io.wojciechosak.calendar.utils.monthLength
import io.wojciechosak.calendar.utils.today
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.plus

@Composable
fun CalendarView(
    state: State<CalendarConfig> = rememberCalendarState(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceEvenly,
    isToday: (LocalDate) -> Boolean = { LocalDate.today() == it },
    day: @Composable (DayState) -> Unit = { dayState ->
        CalendarDay(dayState)
    },
    header: @Composable (month: Month, year: Int) -> Unit = { month, year ->
        MonthHeader(month, year)
    },
    dayOfWeekLabel: @Composable (dayOfWeek: DayOfWeek) -> Unit = { dayOfWeek ->
        val day = when (dayOfWeek) {
            DayOfWeek.MONDAY -> "Mon"
            DayOfWeek.TUESDAY -> "Tue"
            DayOfWeek.WEDNESDAY -> "Wed"
            DayOfWeek.THURSDAY -> "Thu"
            DayOfWeek.FRIDAY -> "Fri"
            DayOfWeek.SATURDAY -> "Sat"
            DayOfWeek.SUNDAY -> "Sun"
            else -> ""
        }
        Text(day, fontSize = 12.sp, textAlign = TextAlign.Center)
    },
    modifier: Modifier = Modifier
) {
    val date = state.value.focusDate
    val daysInCurrentMonth = monthLength(date.month, year = date.year)
    val previousMonthDays = calculateVisibleDaysOfPreviousMonth(date)
    val nextMonthDays =
        if (state.value.showNextMonthDays) calculateVisibleDaysOfNextMonth(date) else 0
    if (state.value.showHeader) {
        header(date.month, date.year)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        userScrollEnabled = false,
        modifier = modifier
    ) {
        var iteration = 0
        val state = state.value
        val weekDaysCount = if (state.showWeekdays) 7 else 0
        items(previousMonthDays + daysInCurrentMonth + nextMonthDays + weekDaysCount) {
            val isWeekdayLabel = state.showWeekdays && iteration < weekDaysCount
            val previousMonthDay =
                iteration >= weekDaysCount && iteration < weekDaysCount + previousMonthDays
            val nextMonthDay =
                iteration >= weekDaysCount + previousMonthDays + daysInCurrentMonth
            var newDate = date.copy(day = 1)
            if (previousMonthDay) {
                newDate =
                    newDate.plus(iteration - weekDaysCount - previousMonthDays, DateTimeUnit.DAY)
            } else if (nextMonthDay) {
                newDate = newDate.plus(1, DateTimeUnit.MONTH)
                    .copy(day = it - previousMonthDays - weekDaysCount - daysInCurrentMonth + 1)
            } else if (!isWeekdayLabel) {
                newDate = newDate.copy(day = iteration - previousMonthDays - weekDaysCount + 1)
            }
            newDate = newDate.plus(state.dayOfWeekOffset, DateTimeUnit.DAY)

            if (state.showWeekdays && iteration + state.dayOfWeekOffset < 7 + state.dayOfWeekOffset) {
                val dayOfWeekIndex =
                    if (iteration + state.dayOfWeekOffset >= DayOfWeek.entries.size) {
                        iteration + state.dayOfWeekOffset - DayOfWeek.entries.size
                    } else if (iteration + state.dayOfWeekOffset < 0)
                        DayOfWeek.entries.size + iteration + state.dayOfWeekOffset
                    else {
                        iteration + state.dayOfWeekOffset
                    }
                dayOfWeekLabel(DayOfWeek.entries[dayOfWeekIndex])
            } else if ((!state.showPreviousMonthDays && previousMonthDay) || (!state.showNextMonthDays && nextMonthDay)) {
                Text("")
            } else {
                day(
                    DayState(
                        date = newDate,
                        isToday = isToday(newDate),
                        isForPreviousMonth = previousMonthDay,
                        isForNextMonth = nextMonthDay,
                        isInDatesRange = newDate >= state.minDate && newDate <= state.maxDate
                    )
                )
            }
            iteration++
        }
    }
}

private fun calculateVisibleDaysOfPreviousMonth(dateTime: LocalDate): Int {
    val firstDayOfMonth = dateTime.copy(day = 1)
    return firstDayOfMonth.dayOfWeek.ordinal
}

private fun calculateVisibleDaysOfNextMonth(dateTime: LocalDate): Int {
    val daysInMonth = monthLength(dateTime.month, dateTime.year)
    val lastMonthDay = dateTime.copy(day = daysInMonth)
    return 6 - lastMonthDay.dayOfWeek.ordinal
}
