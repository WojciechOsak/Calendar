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

/**
 * Composable function to display a vertical calendar view.
 *
 * @param startDate The start date of the calendar.
 * @param calendarAnimator The animator used for animating calendar transitions.
 * @param modifier The modifier for styling and layout of the calendar.
 * @param pageSize The size of each page in the calendar. Default is [PageSize.Fill].
 * @param contentPadding The padding applied around the content of the calendar.
 * @param beyondBoundsPageCount The number of pages to keep loaded beyond the visible bounds. Default is 0.
 * @param calendarView The composable function to display the content of each calendar page.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalCalendarView(
	startDate: LocalDate,
	calendarAnimator: CalendarAnimator = CalendarAnimator(startDate),
	modifier: Modifier = Modifier,
	pageSize: PageSize = PageSize.Fill,
	contentPadding: PaddingValues = PaddingValues(0.dp),
	beyondViewportPageCount: Int = 0,
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
		beyondViewportPageCount = beyondViewportPageCount,
		contentPadding = contentPadding,
	) {
		val index = it - INITIAL_PAGE_INDEX
		calendarView(index)
	}
}
