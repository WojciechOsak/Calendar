package io.wojciechosak.calendar.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import io.wojciechosak.calendar.range.RangeConfig
import kotlinx.datetime.LocalDate

/**
 * Internal function to modify the drawing behavior to illustrate date ranges.
 *
 * @param date The date to modify the drawing for.
 * @param selectedDates The list of selected dates.
 * @param config The configuration for drawing the range. Default is null.
 * @return A modified modifier with the drawing behavior adjusted for illustrating date ranges.
 */
internal fun Modifier.drawRange(
	date: LocalDate,
	selectedDates: List<LocalDate>,
	config: RangeConfig? = null,
) = composed {
	if (config == null) return@composed this

	drawBehind {
		with(config) {
			val range =
				if (selectedDates.size == 2) {
					if (selectedDates.first() >= selectedDates.last()) {
						Pair(selectedDates.last(), selectedDates.first())
					} else {
						Pair(selectedDates.first(), selectedDates.last())
					}
				} else {
					null
				}

			if (range != null && date == range.second) {
				rangeIllustrator.drawEnd(this@drawBehind)
			} else if (range != null && date == range.first) {
				rangeIllustrator.drawStart(this@drawBehind)
			} else if (range != null && date in (range.first..range.second)) {
				rangeIllustrator.drawMiddle(this@drawBehind)
			}
		}
	}
}
