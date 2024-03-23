package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.PageSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.VerticalCalendarView
import kotlinx.datetime.LocalDate

class VerticalCalendarScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val startDate = LocalDate.today()
        Column {
            VerticalCalendarView(
                startDate = startDate,
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
                            startDate = startDate,
                            monthOffset = monthOffset,
                        ),
                )
            }
        }
    }
}
