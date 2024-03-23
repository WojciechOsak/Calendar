package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.wojciechosak.calendar.animation.CalendarAnimator
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.daySimpleName
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import io.wojciechosak.calendar.view.WeekView
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

class AnimationScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val target = LocalDate(1995, Month.NOVEMBER, 12)
        val startDate by remember { mutableStateOf(LocalDate.today()) }
        val calendarAnimator by remember { mutableStateOf(CalendarAnimator(startDate)) }
        val weekCalendarAnimator by remember { mutableStateOf(CalendarAnimator(startDate)) }
        var selectedWeekDate by remember { mutableStateOf<LocalDate?>(null) }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalCalendarView(
                startDate = startDate,
                calendarAnimator = calendarAnimator,
            ) { monthOffset ->
                CalendarView(
                    day = { dayState ->
                        CalendarDay(
                            state = dayState,
                            onClick = { },
                        )
                    },
                    config =
                        rememberCalendarState(
                            startDate = startDate,
                            monthOffset = monthOffset,
                        ),
                )
            }

            Button(onClick = {
                coroutineScope.launch {
                    calendarAnimator.animateTo(target)
                }
                coroutineScope.launch {
                    weekCalendarAnimator.animateTo(target)
                }
            }) {
                Text("Animate to ${target.dayOfMonth}/${target.month}/${target.year}")
            }

            WeekView(
                startDate = startDate,
                calendarAnimator = weekCalendarAnimator,
            ) { state ->
                Text(state.date.daySimpleName())
                CalendarDay(
                    state,
                    modifier = Modifier.width(58.dp),
                    onClick = { selectedWeekDate = state.date },
                )
            }
            if (selectedWeekDate != null) {
                Text("$selectedWeekDate")
            }
        }
    }
}
