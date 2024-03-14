package io.wojciechosak.calendar.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.animation.CalendarAnimator
import io.wojciechosak.calendar.config.CalendarConstants.INITIAL_PAGE_INDEX
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
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekView(
    startDate: LocalDate =
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .toLocalDate(),
    minDate: LocalDate = startDate.copy(day = 1).minus(3, DateTimeUnit.MONTH),
    maxDate: LocalDate =
        startDate.copy(day = monthLength(startDate.month, startDate.year))
            .plus(3, DateTimeUnit.MONTH),
    daysOffset: Int = 0,
    showDaysBesideRange: Boolean = true,
    calendarAnimator: CalendarAnimator = CalendarAnimator(startDate),
    isActive: (LocalDate) -> Boolean = {
        val today =
            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toLocalDate()
        today == it
    },
    modifier: Modifier = Modifier,
    day: @Composable (dayState: DayState) -> Unit = { state ->
        weekDay(state) {
            CalendarDay(
                state,
                modifier = Modifier.width(58.dp),
            )
        }
    },
) {
    val minIndex = if (showDaysBesideRange) 0 else minDate.daysUntil(startDate)
    val maxIndex = if (showDaysBesideRange) Int.MAX_VALUE else startDate.daysUntil(maxDate)
    val initialPageIndex = if (showDaysBesideRange) INITIAL_PAGE_INDEX else minIndex + daysOffset
    LaunchedEffect(Unit) {
        calendarAnimator.setAnimationMode(CalendarAnimator.AnimationMode.WEEK)
    }
    val pagerState =
        rememberPagerState(
            initialPage = initialPageIndex,
            pageCount = { minIndex + maxIndex },
        )
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
    ) {
        val index = it - initialPageIndex // week number
        calendarAnimator.updatePagerState(pagerState)
        for (a in 0..6) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val newDate = startDate.plus(index * 7 + a, DateTimeUnit.DAY)
                day(
                    DayState(
                        date = newDate,
                        isActiveDay = isActive(newDate),
                        enabled = true,
                    ),
                )
            }
        }
    }
}

@Composable
private fun weekDay(
    state: DayState,
    function: @Composable () -> Unit,
) {
    val weekDay =
        when (state.date.dayOfWeek) {
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
