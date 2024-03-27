package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.LocalDate

class HorizontalCalendarScreen : NamedScreen {
    override val name: String
        get() = "Horizontal calendar"

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val startDate = LocalDate.today()
        Column {
            HorizontalCalendarView(startDate = startDate) { monthOffset ->
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
            HorizontalCalendarView(startDate = startDate) { monthOffset ->
                CalendarView(
                    day = { dayState ->
                        SquareDay(dayState.date)
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

@Composable
private fun SquareDay(date: LocalDate) {
    Column(
        modifier =
        Modifier
            .aspectRatio(1f)
            .background(Color.Black)
            .border(BorderStroke(1.dp, Color.White)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "${date.dayOfMonth}",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
    }
}
