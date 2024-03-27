package io.wojciechosak.calendar.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import io.wojciechosak.calendar.modifiers.passTouchGesture
import kotlinx.datetime.Month

/**
 * Composable function to display a month picker with selectable months.
 * View is displayed as scrollable list.
 *
 * @param columns The number of columns in the grid layout of the month picker. Default is 4.
 * @param horizontalArrangement The horizontal alignment of items in the grid layout. Default is [Alignment.CenterHorizontally].
 * @param verticalArrangement The vertical arrangement of items in the grid layout. Default is [Arrangement.Center].
 * @param modifier The modifier for styling and layout of the month picker.
 * @param userScrollEnabled Whether scrolling is enabled for the month picker. Default is true.
 * @param monthCount The total number of months to display in the picker. Default is 12.
 * @param onMonthSelected The callback invoked when a month is selected.
 * @param monthView The composable function to display each month item in the picker.
 */
@Composable
fun MonthPicker(
    columns: Int = 4,
    horizontalArrangement: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    modifier: Modifier = Modifier,
    userScrollEnabled: Boolean = true,
    monthCount: Int = 12,
    onMonthSelected: (Month) -> Unit = {},
    monthView: @Composable (month: Month) -> Unit = { month ->
        Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalArrangement,
            modifier = Modifier.aspectRatio(1f),
        ) {
            Text(
                month.name,
                textAlign = TextAlign.Center,
            )
        }
    },
) {
    require(monthCount in 0..12) {
        throw IllegalArgumentException("Month count should be greater than 0 and <= 12!")
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        userScrollEnabled = userScrollEnabled,
        modifier = modifier,
    ) {
        items(monthCount) { index ->
            val selectedMonth = Month.entries.getOrNull(index)
            Box(
                modifier =
                Modifier.passTouchGesture {
                    selectedMonth?.let { month -> onMonthSelected(month) }
                },
                contentAlignment = Alignment.Center,
            ) {
                selectedMonth?.let { month -> monthView(month) }
            }
        }
    }
}
