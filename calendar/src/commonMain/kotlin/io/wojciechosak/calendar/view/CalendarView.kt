package io.wojciechosak.calendar.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.config.CalendarConfig
import io.wojciechosak.calendar.config.DayState
import io.wojciechosak.calendar.config.YearMonth
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.monthLength
import io.wojciechosak.calendar.utils.today
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.plus

@Composable
fun CalendarView(
    config: State<CalendarConfig> = rememberCalendarState(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceEvenly,
    isActiveDay: (LocalDate) -> Boolean = { LocalDate.today() == it },
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
    val yearMonth by remember { mutableStateOf(config.value.yearMonth) }
    val daysInCurrentMonth by remember {
        mutableStateOf(
            monthLength(
                year = yearMonth.year,
                month = yearMonth.month
            )
        )
    }
    val previousMonthDays by remember { mutableStateOf(calculateVisibleDaysOfPreviousMonth(yearMonth)) }
    val nextMonthDays by remember {
        mutableStateOf(
            if (config.value.showNextMonthDays) calculateVisibleDaysOfNextMonth(
                yearMonth
            ) else 0
        )
    }

    if (config.value.showHeader) {
        header(yearMonth.month, yearMonth.year)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        userScrollEnabled = false,
        modifier = modifier
    ) {
        val state = config.value
        val weekDaysCount = if (state.showWeekdays) 7 else 0
        items(previousMonthDays + daysInCurrentMonth + nextMonthDays + weekDaysCount) { iteration ->
            val isWeekdayLabel = state.showWeekdays && iteration < weekDaysCount
            val previousMonthDay =
                iteration >= weekDaysCount && iteration < weekDaysCount + previousMonthDays
            val nextMonthDay =
                iteration >= weekDaysCount + previousMonthDays + daysInCurrentMonth
            var newDate = LocalDate(year = yearMonth.year, month = yearMonth.month, dayOfMonth = 1)

            if (previousMonthDay && config.value.showPreviousMonthDays) {
                newDate =
                    newDate.plus(iteration - weekDaysCount - previousMonthDays, DateTimeUnit.DAY)
            } else if (nextMonthDay && config.value.showNextMonthDays) {
                newDate = newDate
                    .plus(1, DateTimeUnit.MONTH)
                    .plus(
                        iteration - previousMonthDays - weekDaysCount - daysInCurrentMonth,
                        DateTimeUnit.DAY
                    )
            } else if (!isWeekdayLabel) {
                newDate =
                    newDate.plus(iteration - previousMonthDays - weekDaysCount, DateTimeUnit.DAY)
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
                        isActiveDay = isActiveDay(newDate),
                        isForPreviousMonth = previousMonthDay,
                        isForNextMonth = nextMonthDay,
                        enabled = newDate >= state.minDate && newDate <= state.maxDate
                    )
                )
            }
        }
    }
}

private fun calculateVisibleDaysOfPreviousMonth(yearMonth: YearMonth): Int {
    val (year, month) = yearMonth
    return LocalDate(year = year, month = month, dayOfMonth = 1).dayOfWeek.ordinal
}

private fun calculateVisibleDaysOfNextMonth(yearMonth: YearMonth): Int {
    val (year, month) = yearMonth
    val daysInMonth = monthLength(month, year)
    val lastMonthDay = LocalDate(year = year, month = month, dayOfMonth = daysInMonth)
    return 6 - lastMonthDay.dayOfWeek.ordinal
}
