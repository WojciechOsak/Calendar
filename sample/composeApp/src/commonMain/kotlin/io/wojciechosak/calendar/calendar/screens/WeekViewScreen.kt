package io.wojciechosak.calendar.calendar.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.wojciechosak.calendar.utils.daySimpleName
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarDay
import io.wojciechosak.calendar.view.WeekView
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class WeekViewScreen : NamedScreen {
    override val name: String
        get() = "Week calendar"

    @Composable
    override fun Content() {
        Column(modifier = Modifier.padding(10.dp)) {
            var selectedDay by remember { mutableStateOf<LocalDate?>(null) }

            WeekView { state ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.date.daySimpleName())
                    CalendarDay(
                        state,
                        onClick = { },
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("20 days range, with disabled next three days:")
            Spacer(modifier = Modifier.height(20.dp))

            WeekView(
                startDate = LocalDate.today(),
                minDate = LocalDate.today().minus(9, DateTimeUnit.DAY),
                maxDate = LocalDate.today().plus(9, DateTimeUnit.DAY),
                isActive = { it == selectedDay },
            ) { state ->
                val isInNextThreeDays =
                    state.date in LocalDate
                        .today()
                        .plus(1, DateTimeUnit.DAY)..LocalDate.today()
                        .plus(3, DateTimeUnit.DAY)
                CalendarDay(
                    state = state.copy(enabled = !isInNextThreeDays),
                    onClick = { selectedDay = state.date },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (selectedDay != null) {
                Text("Selected: $selectedDay")
            }
        }
    }
}
