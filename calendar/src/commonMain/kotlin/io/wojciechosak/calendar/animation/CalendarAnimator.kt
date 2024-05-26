package io.wojciechosak.calendar.animation

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import io.wojciechosak.calendar.config.CalendarConstants.INITIAL_PAGE_INDEX
import io.wojciechosak.calendar.utils.copy
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.periodUntil
import kotlinx.datetime.plus
import kotlin.math.floor

/**
 * Class for animating the transition between dates in a calendar.
 *
 * @property startDate The initial start date of the calendar.
 */
@OptIn(ExperimentalFoundationApi::class)
class CalendarAnimator(private val startDate: LocalDate) {
	enum class AnimationMode {
		MONTH,
		DAY,
		WEEK,
	}

	private var pagerState: PagerState? = null

	private var mode: AnimationMode = AnimationMode.MONTH

	internal fun updatePagerState(pagerState: PagerState) {
		this.pagerState = pagerState
	}

	internal fun setAnimationMode(mode: AnimationMode) {
		this.mode = mode
	}

	suspend fun animateToNextPage() {
		pagerState?.animateScrollToPage(pagerState!!.currentPage + 1)
	}

	suspend fun animateToPreviousPage() {
		pagerState?.animateScrollToPage(pagerState!!.currentPage - 1)
	}

	suspend fun scrollToPage(page: Int, pageOffsetFraction: Float = 0f) {
		pagerState?.scrollToPage(page, pageOffsetFraction = pageOffsetFraction)
	}

	suspend fun animateTo(
		target: LocalDate,
		pageOffsetFraction: Float = 0f,
		animationSpec: AnimationSpec<Float> = spring(stiffness = Spring.StiffnessMediumLow),
	) {
		val initialPage = INITIAL_PAGE_INDEX
		val currentDate =
			when (mode) {
				AnimationMode.MONTH ->
					startDate.plus(
						(pagerState?.targetPage ?: 0) - initialPage,
						DateTimeUnit.MONTH,
					)

				AnimationMode.DAY ->
					startDate.plus(
						(pagerState?.targetPage ?: 0) - initialPage,
						DateTimeUnit.DAY,
					)

				AnimationMode.WEEK ->
					startDate.plus(
						(pagerState?.targetPage ?: 0) - initialPage,
						DateTimeUnit.WEEK,
					)
			}
		val targetDate =
			target.run {
				if (mode == AnimationMode.MONTH) {
					copy(day = currentDate.dayOfMonth)
				} else {
					this
				}
			}
		val diff = currentDate.periodUntil(targetDate)
		val offset =
			when (mode) {
				AnimationMode.MONTH -> diff.months + diff.years * 12
				AnimationMode.DAY -> currentDate.daysUntil(targetDate)
				AnimationMode.WEEK -> floor(currentDate.daysUntil(targetDate) / 7f).toInt()
			}
		if (initialPage + offset > 0) {
			pagerState?.animateScrollToPage(
				page = (pagerState?.settledPage ?: initialPage) + offset,
				pageOffsetFraction = pageOffsetFraction,
				animationSpec = animationSpec,
			)
		}
	}
}
