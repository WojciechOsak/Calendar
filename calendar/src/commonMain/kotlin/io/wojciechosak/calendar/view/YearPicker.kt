package io.wojciechosak.calendar.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.wojciechosak.calendar.config.CalendarConstants
import io.wojciechosak.calendar.config.CalendarConstants.MAX_PAGES
import io.wojciechosak.calendar.modifiers.passTouchGesture
import io.wojciechosak.calendar.utils.today
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YearPicker(
    columns: Int = 4,
    rows: Int = 3,
    startDate: LocalDate = LocalDate.today(),
    mode: YearPickerMode = YearPickerMode.HORIZONTAL,
    yearOffset: Int = 0,
    modifier: Modifier = Modifier,
    pageSize: PageSize = PageSize.Fill,
    onYearSelected: (Int) -> Unit = {},
    yearView: @Composable (year: Int) -> Unit = { year ->
        Text(year.toString(), modifier = Modifier.padding(32.dp))
    },
) {
    val pagerState =
        rememberPagerState(
            initialPage = CalendarConstants.INITIAL_PAGE_INDEX,
            pageCount = { MAX_PAGES },
        )
    when (mode) {
        YearPickerMode.VERTICAL -> {
            VerticalPager(
                state = pagerState,
                modifier = modifier,
                pageSize = pageSize,
            ) { page ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth(),
                ) {
                    VerticalPage(
                        page = page,
                        startDate = startDate,
                        columns = columns,
                        rows = rows,
                        onYearSelected = onYearSelected,
                        yearView = yearView,
                        yearOffset = yearOffset,
                    )
                }
            }
        }

        YearPickerMode.HORIZONTAL -> {
            HorizontalPager(
                state = pagerState,
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                pageSize = pageSize,
            ) { page ->
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    HorizontalPage(
                        page = page,
                        startDate = startDate,
                        columns = columns,
                        rows = rows,
                        onYearSelected = onYearSelected,
                        yearView = yearView,
                        yearOffset = yearOffset,
                    )
                }
            }
        }
    }
}

@Composable
private fun VerticalPage(
    columns: Int,
    rows: Int,
    startDate: LocalDate,
    page: Int,
    yearOffset: Int,
    onYearSelected: (Int) -> Unit = {},
    yearView: @Composable (Int) -> Unit,
) {
    for (column in 0..<columns) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            for (row in 0..<rows) {
                val yearValue =
                    startDate.year + row * columns + column + (page - CalendarConstants.INITIAL_PAGE_INDEX) * rows * columns + yearOffset

                Row(
                    modifier = Modifier.passTouchGesture { onYearSelected(yearValue) },
                    horizontalArrangement = Arrangement.Center,
                ) {
                    yearView(yearValue)
                }
            }
        }
    }
}

@Composable
private fun HorizontalPage(
    columns: Int,
    rows: Int,
    startDate: LocalDate,
    page: Int,
    yearOffset: Int,
    onYearSelected: (Int) -> Unit = {},
    yearView: @Composable (Int) -> Unit,
) {
    for (column in 0..<rows) {
        Row(Modifier) {
            for (row in 0..<columns) {
                val yearValue =
                    startDate.year + column * rows + row + (page - CalendarConstants.INITIAL_PAGE_INDEX) * rows * columns + yearOffset
                Column(Modifier.passTouchGesture { onYearSelected(yearValue) }) {
                    yearView(yearValue)
                }
            }
        }
    }
}

enum class YearPickerMode {
    VERTICAL,
    HORIZONTAL,
}
