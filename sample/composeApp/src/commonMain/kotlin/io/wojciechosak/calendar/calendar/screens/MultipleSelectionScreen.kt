package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import io.wojciechosak.calendar.config.SelectionMode
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.LocalDate

class MultipleSelectionScreen : NamedScreen {
    override val name: String
        get() = "Multiple dates selector"

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Column {
            val startDate = LocalDate.today()

            HorizontalCalendarView(startDate = startDate) { monthOffset ->
                val config =
                    rememberCalendarState(
                        startDate = startDate,
                        monthOffset = monthOffset,
                    )
                CalendarView(
                    config = config,
                    isActiveDay = { it in config.value.selectedDates },
                    selectionMode = SelectionMode.Multiply(3),
                )
            }
        }
    }
}
