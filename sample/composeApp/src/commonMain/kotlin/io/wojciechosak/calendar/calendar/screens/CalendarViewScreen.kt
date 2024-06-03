package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.config.MonthYear
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.config.toLocalDate
import io.wojciechosak.calendar.view.CalendarView
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlin.random.Random

class CalendarViewScreen : NamedScreen {
    override val name: String
        get() = "CalendarView"

    @Composable
    override fun Content() {
        CalendarView(
            day = { state ->
                DayView(
                    date = state.date,
                    isDotVisible = state.isActiveDay || Random.nextBoolean(),
                    onClick = { },
                )
            },
            config =
            rememberCalendarState(
                startDate = MonthYear(year = 1994, month = Month.APRIL).toLocalDate(),
                monthOffset = 0,
                showNextMonthDays = false,
                showPreviousMonthDays = false,
                showHeader = false,
                showWeekdays = false,
            ),
        )
    }
}

@Composable
private fun DayView(
    date: LocalDate,
    onClick: () -> Unit = {},
    isDotVisible: Boolean = true,
    modifier: Modifier = Modifier,
) {
    Box {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier.size(55.dp).aspectRatio(1f).padding(3.dp),
            contentPadding = PaddingValues(0.dp),
            border = BorderStroke(0.dp, Color.Transparent),
            colors =
            ButtonDefaults.outlinedButtonColors(
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
                modifier =
                Modifier
                    .padding(bottom = 10.dp)
                    .size(8.dp)
                    .align(Alignment.BottomCenter),
                onDraw = { drawCircle(color = Color(0xff2d2cb2)) },
            )
        }
    }
}
