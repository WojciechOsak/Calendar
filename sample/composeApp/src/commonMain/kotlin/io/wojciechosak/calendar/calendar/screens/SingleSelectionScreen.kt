package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
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
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.LocalDate

class SingleSelectionScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Column {
            var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
            val startDate = LocalDate.today()
            HorizontalCalendarView(startDate = startDate) { monthOffset ->
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
                            startDate = startDate,
                            monthOffset = monthOffset,
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
