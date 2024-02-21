package io.wojciechosak.calendar.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCalendarView(
    modifier: Modifier = Modifier,
    calendarView: @Composable (monthOffset: Int) -> Unit,
) {
    HorizontalPager(
        state = rememberPagerState(
            initialPage = Int.MAX_VALUE / 2,
            pageCount = { Int.MAX_VALUE },
        ),
        modifier = modifier.fillMaxWidth(),
        beyondBoundsPageCount = 0,
    ) {
        val index = it - Int.MAX_VALUE / 2
        Column {
            calendarView(index)
        }
    }
}