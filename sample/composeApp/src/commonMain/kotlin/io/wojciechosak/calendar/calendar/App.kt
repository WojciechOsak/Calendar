package io.wojciechosak.calendar.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import io.wojciechosak.calendar.view.WeekView
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

@Composable
internal fun App() {
    Column(modifier = Modifier.width(400.dp)) {

        val today = LocalDate(1995, monthNumber = 7, dayOfMonth = 4)
        WeekView(
            date = today,
            minDate = LocalDate(1990, monthNumber = 1, dayOfMonth = 1),
            maxDate = LocalDate(2050, monthNumber = 12, dayOfMonth = 31),
        )

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

        Spacer(Modifier.height(30.dp))

        CalendarView(
            date = LocalDate(dayOfMonth = 3, year = 1994, monthNumber = 4),
            day = { date, isToday, isForPreviousMonth, isForNextMonth ->
                Sample2(
                    date = date,
                    isDotVisible = isToday || Random.nextBoolean()
                )
            },
            showWeekdays = false,
            showPreviousMonthDays = false,
            showNextMonthDays = false,
            showHeader = false
        )
    }
}

@Composable
private fun Sample2(
    date: LocalDate,
    onClick: () -> Unit = {},
    isDotVisible: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Box {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.aspectRatio(1f).padding(3.dp),
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(0.dp, Color.Transparent),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xffdaa92a),
            ),
        ) {
            Text(
                "${date.dayOfMonth}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
            )
        }
        if (isDotVisible) {
            Canvas(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .size(8.dp)
                    .align(Alignment.BottomCenter),
                onDraw = { drawCircle(color = Color(0xff2d2cb2)) })
        }
    }
}


@Composable
private fun Sample3(
    date: LocalDate,
) {
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .background(Color.Black)
            .border(BorderStroke(1.dp, Color.White)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "${date.dayOfMonth}",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
        )
    }
}