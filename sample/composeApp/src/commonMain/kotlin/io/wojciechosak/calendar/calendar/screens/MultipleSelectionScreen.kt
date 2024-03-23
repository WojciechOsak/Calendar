package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.LocalDate

class MultipleSelectionScreen : Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        Column {
            val startDate = LocalDate.today()
            val selectedDates = remember { mutableStateListOf<LocalDate>() }

            HorizontalCalendarView(startDate = startDate) { monthOffset ->
                CalendarView(
                    config =
                        rememberCalendarState(
                            startDate = startDate,
                            monthOffset = monthOffset,
                            selectedDates = selectedDates,
                        ),
                    isActiveDay = { it in selectedDates },
                    onDateSelected = {
                        selectedDates.clear()
                        selectedDates.addAll(it)
                    },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (selectedDates.isNotEmpty()) {
                Text("Selected:\n${selectedDates.map { "$it\n" }}")
            }
        }
    }
}
