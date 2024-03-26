package io.wojciechosak.calendar.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.wojciechosak.calendar.animation.CalendarAnimator
import io.wojciechosak.calendar.config.CalendarConstants.INITIAL_PAGE_INDEX
import io.wojciechosak.calendar.config.CalendarConstants.MAX_PAGES
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalCalendarView(
    startDate: LocalDate,
    calendarAnimator: CalendarAnimator = CalendarAnimator(startDate),
    modifier: Modifier = Modifier,
    pageSize: PageSize = PageSize.Fill,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    beyondBoundsPageCount: Int = 3,
    calendarView: @Composable (monthOffset: Int) -> Unit,
) {
    val pagerState =
        rememberPagerState(
            initialPage = INITIAL_PAGE_INDEX,
            pageCount = { MAX_PAGES },
        )
    LaunchedEffect(pagerState) {
        calendarAnimator.setAnimationMode(CalendarAnimator.AnimationMode.MONTH)
        calendarAnimator.updatePagerState(pagerState)
    }
    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth(),
        pageSize = pageSize,
        beyondBoundsPageCount = beyondBoundsPageCount,
        contentPadding = contentPadding,
    ) {
        val index = it - INITIAL_PAGE_INDEX
        calendarView(index)
    }
}
