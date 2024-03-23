package io.wojciechosak.calendar.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.unit.dp
import io.wojciechosak.calendar.animation.CalendarAnimator
import io.wojciechosak.calendar.config.CalendarConstants.INITIAL_PAGE_INDEX
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalCalendarView(
    startDate: LocalDate,
    modifier: Modifier = Modifier,
    pageSize: PageSize = PageSize.Fill,
    beyondBoundsPageCount: Int = 0,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageNestedScrollConnection: NestedScrollConnection =
        PagerDefaults.pageNestedScrollConnection(
            Orientation.Horizontal,
        ),
    calendarAnimator: CalendarAnimator = CalendarAnimator(startDate),
    calendarView: @Composable (monthOffset: Int) -> Unit,
) {
    val pagerState =
        rememberPagerState(
            initialPage = INITIAL_PAGE_INDEX,
            pageCount = { Int.MAX_VALUE },
        )

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        pageSize = pageSize,
        beyondBoundsPageCount = beyondBoundsPageCount,
        pageNestedScrollConnection = pageNestedScrollConnection,
        contentPadding = contentPadding,
    ) {
        val index = it - INITIAL_PAGE_INDEX
        calendarAnimator.updatePagerState(pagerState)
        LaunchedEffect(Unit) {
            calendarAnimator.setAnimationMode(CalendarAnimator.AnimationMode.MONTH)
        }
        Column {
            calendarView(index)
        }
    }
}
