package io.wojciechosak.calendar.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.config.DayState
import io.wojciechosak.calendar.utils.copy
import io.wojciechosak.calendar.utils.monthLength
import io.wojciechosak.calendar.utils.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@Composable
fun WeekView(
    date: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .toLocalDate(),
    minDate: LocalDate = date.copy(day = 1),
    maxDate: LocalDate = date.copy(day = monthLength(date.month, date.year)),
    minimumDaysVisible: Int = 7,
    isToday: (LocalDate) -> Boolean = {
        val today =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toLocalDate()
        today == it
    },
    day: @Composable (dayState: DayState) -> Unit = { state ->
        weekDay(state) {
            CalendarDay(
                state,
                modifier = Modifier.width(58.dp),
            )
        }
    },
) {
    require(date in minDate..maxDate) {
        throw IllegalArgumentException("Provided date should be in range of minDate and maxDate.")
    }
    LazyRow(
        state = rememberLazyListState(
            initialFirstVisibleItemIndex = (minDate.daysUntil(date) - 3).coerceAtLeast(0),
            initialFirstVisibleItemScrollOffset = 0
        )
    ) {
        items((minDate.daysUntil(maxDate) + 1).coerceAtLeast(minimumDaysVisible.coerceAtLeast(1))) { index ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val newDate = minDate.plus(index, DateTimeUnit.DAY)
                day(DayState(date = newDate, isToday = isToday(newDate), isInDatesRange = newDate in minDate..maxDate))
            }
        }
    }
}

@Composable
private fun weekDay(
    state: DayState,
    function: @Composable () -> Unit,
) {
    val weekDay = when (state.date.dayOfWeek) {
        DayOfWeek.MONDAY -> "Mon"
        DayOfWeek.TUESDAY -> "Tue"
        DayOfWeek.WEDNESDAY -> "Wed"
        DayOfWeek.THURSDAY -> "Thu"
        DayOfWeek.FRIDAY -> "Fri"
        DayOfWeek.SATURDAY -> "Sat"
        DayOfWeek.SUNDAY -> "Sun"
        else -> ""
    }
    Text(weekDay, fontSize = 12.sp, textAlign = TextAlign.Center)
    function()
}