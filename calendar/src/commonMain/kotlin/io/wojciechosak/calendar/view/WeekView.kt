package io.wojciechosak.calendar.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.animation.CalendarAnimator
import io.wojciechosak.calendar.config.CalendarConstants.INITIAL_PAGE_INDEX
import io.wojciechosak.calendar.config.CalendarConstants.MAX_PAGES
import io.wojciechosak.calendar.config.DayState
import io.wojciechosak.calendar.utils.Pallete
import io.wojciechosak.calendar.utils.copy
import io.wojciechosak.calendar.utils.daySimpleName
import io.wojciechosak.calendar.utils.monthLength
import io.wojciechosak.calendar.utils.toLocalDate
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

/**
 * Composable function to display a week view with selectable days.
 *
 * @param startDate The start date of the week view. Default is the current date.
 * @param minDate The minimum selectable date in the week view. Default is three months before the start date.
 * @param maxDate The maximum selectable date in the week view. Default is three months after the end date of the month containing the start date.
 * @param daysOffset The offset in days from the start date. Default is 0.
 * @param showDaysBesideRange Whether to show days beside the range. Default is true.
 * @param calendarAnimator The animator used for animating calendar transitions.
 * @param isActive A lambda function to determine if a date is considered active. Default is comparison with the current date.
 * @param modifier The modifier for styling and layout of the week view.
 * @param firstVisibleDate A callback invoked with the first visible date in the week view.
 * @param day The composable function to display each day item in the week view.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekView(
	startDate: LocalDate =
		Clock.System.now()
			.toLocalDateTime(TimeZone.currentSystemDefault())
			.toLocalDate(),
	minDate: LocalDate = startDate.copy(day = 1).minus(3, DateTimeUnit.MONTH),
	maxDate: LocalDate =
		startDate.copy(day = monthLength(startDate.month, startDate.year))
			.plus(3, DateTimeUnit.MONTH),
	daysOffset: Int = 0,
	showDaysBesideRange: Boolean = true,
	calendarAnimator: CalendarAnimator = CalendarAnimator(startDate),
	isActive: (LocalDate) -> Boolean = {
		val today =
			Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toLocalDate()
		today == it
	},
	modifier: Modifier = Modifier,
	firstVisibleDate: (LocalDate) -> Unit = {},
	day: @Composable (dayState: DayState) -> Unit = { state ->
		weekDay(state) {
			WeekViewDay(modifier = Modifier, state = state)
		}
	},
) {
	val minIndex = if (showDaysBesideRange) 0 else minDate.daysUntil(startDate)
	val maxIndex = if (showDaysBesideRange) MAX_PAGES else startDate.daysUntil(maxDate)
	val initialPageIndex = if (showDaysBesideRange) INITIAL_PAGE_INDEX else minIndex + daysOffset
	LaunchedEffect(Unit) {
		calendarAnimator.setAnimationMode(CalendarAnimator.AnimationMode.WEEK)
	}
	val pagerState =
		rememberPagerState(
			initialPage = initialPageIndex,
			pageCount = { minIndex + maxIndex },
		)
	HorizontalPager(
		state = pagerState,
		modifier = modifier,
	) {
		val index = it - initialPageIndex // week number
		calendarAnimator.updatePagerState(pagerState)
		firstVisibleDate(startDate.plus((pagerState.currentPage - initialPageIndex) * 7, DateTimeUnit.DAY))
		Row(
			Modifier.fillMaxWidth(),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			for (day in 0..6) {
				Column(
					verticalArrangement = Arrangement.SpaceEvenly,
					horizontalAlignment = Alignment.CenterHorizontally,
				) {
					val newDate = startDate.plus(index * 7 + day, DateTimeUnit.DAY)
					day(
						DayState(
							date = newDate,
							isActiveDay = isActive(newDate),
							enabled = true,
						),
					)
				}
			}
		}
	}
}

@Composable
private fun weekDay(
	state: DayState,
	function: @Composable () -> Unit,
) {
	Text(state.date.daySimpleName(), fontSize = 12.sp, textAlign = TextAlign.Center)
	function()
}

@Composable
fun WeekViewDay(modifier: Modifier = Modifier, state: DayState, onClick: (DayState) -> Unit = {}) {
	OutlinedButton(
		onClick = { onClick(state) },
		modifier = modifier,
		shape = RoundedCornerShape(50.dp),
		border = BorderStroke(1.dp, Color.Transparent),
		contentPadding = PaddingValues(0.dp),
		interactionSource = MutableInteractionSource(),
		enabled = state.enabled,
		colors =
		ButtonDefaults.outlinedButtonColors(
			contentColor =
			if (state.isForPreviousMonth || state.isForNextMonth) {
				Color.LightGray
			} else {
				if (state.isActiveDay) Pallete.LightGreen else Pallete.LightBlue
			},
		),
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Text(
				"${state.date.dayOfMonth}",
				fontSize = 20.sp,
				textAlign = TextAlign.Center,
			)
		}
	}
}