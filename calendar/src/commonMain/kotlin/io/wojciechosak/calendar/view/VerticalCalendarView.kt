package io.wojciechosak.calendar.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalCalendarView(
    modifier: Modifier = Modifier,
    pageSize: PageSize = PageSize.Fill,
    calendarView: @Composable (monthOffset: Int) -> Unit,
) {
    VerticalPager(
        state =
            rememberPagerState(
                initialPage = Int.MAX_VALUE / 2,
                pageCount = { Int.MAX_VALUE },
            ),
        modifier = modifier.fillMaxWidth(),
        pageSize = pageSize,
        beyondBoundsPageCount = 0,
    ) {
        val index = it - Int.MAX_VALUE / 2
        Column {
            calendarView(index)
        }
    }
}
