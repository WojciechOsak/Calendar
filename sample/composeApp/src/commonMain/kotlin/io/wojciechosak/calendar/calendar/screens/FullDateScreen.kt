package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import io.wojciechosak.calendar.config.SelectionMode
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.copy
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import io.wojciechosak.calendar.view.MonthHeader
import io.wojciechosak.calendar.view.MonthPicker
import io.wojciechosak.calendar.view.YearPicker
import kotlinx.datetime.LocalDate

class FullDateScreen : NamedScreen {
    override val name: String
        get() = "Full date selector"

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        var mode by remember { mutableStateOf(DatePickerMode.DAY) }
        var date by remember { mutableStateOf(LocalDate.today()) }

        when (mode) {
            DatePickerMode.YEAR -> {
                YearPicker(
                    onYearSelected = {
                        date = date.copy(year = it)
                        mode = DatePickerMode.DAY
                    },
                )
            }

            DatePickerMode.MONTH -> {
                MonthPicker(onMonthSelected = {
                    date = date.copy(month = it)
                    mode = DatePickerMode.YEAR
                }) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.aspectRatio(1f),
                    ) {
                        Text(
                            text = it.name.substring(IntRange(0, 2)).capitalize(),
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            DatePickerMode.DAY -> {
                HorizontalCalendarView(
                    startDate = date,
                    calendarView = { monthOffset ->
                        CalendarView(
                            config =
                            rememberCalendarState(
                                startDate = date,
                                monthOffset = monthOffset,
                            ),
                            header = { month, year ->
                                Box(modifier = Modifier.clickable { mode = DatePickerMode.MONTH }) {
                                    Text("Click -> ")
                                    MonthHeader(month, year)
                                }
                            },
                            selectionMode = SelectionMode.Single,
                            onDateSelected = {
                                it.firstOrNull()?.let { date = it }
                            },
                        )
                    },
                )
            }
        }
    }
}

private enum class DatePickerMode {
    YEAR,
    MONTH,
    DAY,
}
