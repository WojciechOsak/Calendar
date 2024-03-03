package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.asYearMonth
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus

class SingleSelectionScreen : Screen {
    @Composable
    override fun Content() {
        Column {
            var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
            HorizontalCalendarView { monthOffset ->
                CalendarView(
                    day = { dayState ->
                        CalendarDay(
                            state = dayState,
                            onClick = {
                                selectedDate = dayState.date
                            },
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
                    isActiveDay = {
                        it == selectedDate
                    },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (selectedDate != null) {
                Text("Selected: $selectedDate")
            }
        }
    }
}
