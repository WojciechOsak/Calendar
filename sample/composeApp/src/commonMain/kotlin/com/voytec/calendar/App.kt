package com.voytec.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.voytec.calendar.view.CalendarDay
import com.voytec.calendar.view.CalendarView
import com.voytec.calendar.view.HorizontalCalendarView
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun App() {
    Column {
        Spacer(Modifier.height(20.dp))

        HorizontalCalendarView { monthOffset ->
            CalendarView(
                date = remember {
                    Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date
                }
                    .plus(monthOffset, DateTimeUnit.MONTH),
                day = { date, isToday, isForPreviousMonth, isForNextMonth ->
                    CalendarDay(
                        date = date,
                        isToday = isToday,
                        isForPreviousMonth = isForPreviousMonth,
                        isForNextMonth = isForNextMonth,
                        onClick = { }
                    )
                },
                showWeekdays = true,
                showPreviousMonthDays = true,
                showNextMonthDays = true
            )
        }
    }
}