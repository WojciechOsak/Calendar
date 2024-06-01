package io.wojciechosak.calendar.calendar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import io.wojciechosak.calendar.calendar.screens.AnimationScreen
import io.wojciechosak.calendar.calendar.screens.CalendarViewScreen
import io.wojciechosak.calendar.calendar.screens.FullDateScreen
import io.wojciechosak.calendar.calendar.screens.HorizontalCalendarScreen
import io.wojciechosak.calendar.calendar.screens.MultipleSelectionScreen
import io.wojciechosak.calendar.calendar.screens.RangeSelectionScreen
import io.wojciechosak.calendar.calendar.screens.SingleSelectionScreen
import io.wojciechosak.calendar.calendar.screens.VerticalCalendarScreen
import io.wojciechosak.calendar.calendar.screens.WeekViewScreen

class MenuScreen : Screen {
    @Composable
    override fun Content() {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item { ScreenButton(CalendarViewScreen(), "Calendar view") }
            item { ScreenButton(HorizontalCalendarScreen(), "Horizontal view") }
            item { ScreenButton(VerticalCalendarScreen(), "Vertical view") }
            item { ScreenButton(WeekViewScreen(), "Week view") }
            item { ScreenButton(SingleSelectionScreen(), "Single selection") }
            item { ScreenButton(MultipleSelectionScreen(), "Multiple selection") }
            item { ScreenButton(AnimationScreen(), "Animations") }
            item { ScreenButton(RangeSelectionScreen(), "Range selection") }
            item { ScreenButton(FullDateScreen(), "Full date selector (day/month/year)") }
            item { Text("Lib version: 1.0.1") }
        }
    }

    @Composable
    private fun ScreenButton(
        screen: Screen?,
        title: String,
    ) {
        val navigator = LocalNavigator.current
        Button(
            onClick = { screen?.let { navigator?.push(screen) } },
            modifier = Modifier.fillMaxWidth().padding(10.dp),
        ) {
            Text(title)
        }
    }
}
