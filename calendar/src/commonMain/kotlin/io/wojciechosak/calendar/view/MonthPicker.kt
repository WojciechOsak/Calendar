package io.wojciechosak.calendar.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.wojciechosak.calendar.modifiers.passTouchGesture
import kotlinx.datetime.Month

@Composable
fun MonthPicker(
    columns: Int = 4,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Center,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    modifier: Modifier = Modifier,
    userScrollEnabled: Boolean = true,
    monthCount: Int = 12,
    onMonthSelected: (Month) -> Unit = {},
    monthView: @Composable (month: Month) -> Unit = { month ->
        Text(month.name)
    },
) {
    require(monthCount in 0..12) {
        throw IllegalArgumentException("Month count should be greater than 0 and <= 12!")
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement,
        userScrollEnabled = userScrollEnabled,
        modifier = modifier,
    ) {
        items(monthCount) { index ->
            val selectedMonth = Month.entries.getOrNull(index)
            Box(
                modifier =
                    Modifier.passTouchGesture {
                        selectedMonth?.let { month ->
                            onMonthSelected(
                                month,
                            )
                        }
                    },
                contentAlignment = Alignment.Center,
            ) {
                selectedMonth?.let { month -> monthView(month) }
            }
        }
    }
}
