package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.PageSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.asYearMonth
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.VerticalCalendarView
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class VerticalCalendarScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Column {
            VerticalCalendarView(
                pageSize = PageSize.Fixed(300.dp),
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
                            yearMonth =
                                LocalDate
                                    .today()
                                    .plus(monthOffset, DateTimeUnit.MONTH)
                                    .asYearMonth(),
                            showWeekdays = true,
                            showPreviousMonthDays = true,
                            showNextMonthDays = true,
                        ),
                )
            }
        }
    }
}
