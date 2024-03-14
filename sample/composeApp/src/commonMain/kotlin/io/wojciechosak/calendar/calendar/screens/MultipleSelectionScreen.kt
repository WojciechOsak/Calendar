package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PageSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import io.wojciechosak.calendar.view.VerticalCalendarView
import kotlinx.datetime.LocalDate

class MultipleSelectionScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Column {
            val selectedDates = remember { mutableStateListOf<LocalDate>() }
            val startDate = LocalDate.today()

            HorizontalCalendarView(startDate = startDate) { monthOffset ->
                CalendarView(
                    day = { dayState ->
                        CalendarDay(
                            state = dayState,
                            onClick = {
                                if (selectedDates.size > 2) {
                                    selectedDates.removeFirst()
                                }
                                selectedDates.add(dayState.date)
                            },
                        )
                    },
                    config =
                        rememberCalendarState(
                            startDate = startDate,
                            monthOffset = monthOffset,
                            showWeekdays = true,
                            showPreviousMonthDays = true,
                            showNextMonthDays = true,
                        ),
                    isActiveDay = {
                        it in selectedDates
                    },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (!selectedDates.isEmpty()) {
                Text("Selected:\n${selectedDates.map { "$it\n" }}")
            }
            VerticalCalendarView(
                startDate = startDate,
                pageSize = PageSize.Fixed(300.dp),
            ) { monthOffset ->
                CalendarView(
                    day = { dayState ->
                        CalendarDay(
                            state = dayState,
                            onClick = {
                                if (selectedDates.size > 2) {
                                    selectedDates.removeFirst()
                                }
                                selectedDates.add(dayState.date)
                            },
                        )
                    },
                    config =
                        rememberCalendarState(
                            startDate = startDate,
                            monthOffset = monthOffset,
                            showWeekdays = true,
                            showPreviousMonthDays = true,
                            showNextMonthDays = true,
                        ),
                    isActiveDay = {
                        it in selectedDates
                    },
                )
            }
        }
    }
}
