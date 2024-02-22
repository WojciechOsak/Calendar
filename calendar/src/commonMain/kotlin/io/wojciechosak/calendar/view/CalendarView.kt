package io.wojciechosak.calendar.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.utils.copy
import io.wojciechosak.calendar.utils.monthLength
import io.wojciechosak.calendar.utils.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@Composable
fun CalendarView(
    date: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .toLocalDate(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceEvenly,
    verticalArrangement: Arrangement.Vertical = Arrangement.SpaceEvenly,
    showPreviousMonthDays: Boolean = true,
    showNextMonthDays: Boolean = true,
    showHeader: Boolean = true,
    isToday: (LocalDate) -> Boolean = {
        val today =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toLocalDate()
        today == it
    },
    day: @Composable (
        date: LocalDate,
        isToday: Boolean,
        isForPreviousMonth: Boolean,
        isForNextMonth: Boolean
    ) -> Unit = { dayNumber, isToday, isForPreviousMonth, isForNextMonth ->
        CalendarDay(
            dayNumber,
            isToday = isToday,
            isForPreviousMonth = isForPreviousMonth,
            isForNextMonth = isForNextMonth
        )
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
    showWeekdays: Boolean = true,
    modifier: Modifier = Modifier
) {
    val daysInCurrentMonth = monthLength(date.month, year = date.year)
    val previousMonthDays = calculateVisibleDaysOfPreviousMonth(date)
    val nextMonthDays =
        if (showNextMonthDays) calculateVisibleDaysOfNextMonth(date) else 0
    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toLocalDate()

    if (showHeader) {
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
        val weekDaysCount = if (showWeekdays) 7 else 0

        items(previousMonthDays + daysInCurrentMonth + nextMonthDays + weekDaysCount) {
            val isWeekdayLabel = showWeekdays && iteration < weekDaysCount
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

            if (showWeekdays && iteration < 7) {
                dayOfWeekLabel(DayOfWeek.entries[iteration])
            } else if ((!showPreviousMonthDays && previousMonthDay) || (!showNextMonthDays && nextMonthDay)) {
                Text("")
            } else {
                day(
                    newDate,
                    isToday(newDate),
                    previousMonthDay,
                    nextMonthDay
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
