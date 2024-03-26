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
import io.wojciechosak.calendar.config.SelectionMode
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.range.RangeConfig
import io.wojciechosak.calendar.range.RoundedRangeIllustrator
import io.wojciechosak.calendar.utils.Pallete
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarView
import io.wojciechosak.calendar.view.HorizontalCalendarView
import kotlinx.datetime.LocalDate

class RangeSelectionScreen : NamedScreen {
    override val name: String
        get() = "Range selection"

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
                    selectionMode = SelectionMode.Range,
                    onDateSelected = {
                        selectedDates.clear()
                        selectedDates.addAll(it)
                    },
                    rangeConfig =
                        RangeConfig(
                            rangeIllustrator = RoundedRangeIllustrator(Pallete.LightGreen),
                        ),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (selectedDates.size == 2) {
                Text("From:\n${selectedDates.last()} to ${selectedDates.first()}")
            }
        }
    }
}
