package io.wojciechosak.calendar.range

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Class implementing the RangeIllustrator interface to illustrate date ranges with an underline.
 *
 * @param color The color used for illustrating the underline.
 * @param y A function to calculate the y-coordinate of the underline.
 * Default is the height of the drawing area (so it is drawn on the bottom).
 * @param strokeWidth The width of the underline. Default is 10f.
 */
class UnderlineIllustrator(
	private val color: Color,
	private val y: (Size) -> Float = { it.height },
	private val strokeWidth: Float = 10f,
) : RangeIllustrator {
	override fun drawEnd(drawScope: DrawScope) {
		drawScope.apply {
			drawLine(
				color = color,
				strokeWidth = strokeWidth,
				start = Offset(0f, y(size)),
				end = Offset(size.width, y(size)),
			)
		}
	}

	override fun drawStart(drawScope: DrawScope) {
		drawScope.apply {
			drawLine(
				color = color,
				strokeWidth = strokeWidth,
				start = Offset(0f, y(size)),
				end = Offset(size.width, y(size)),
			)
		}
	}

	override fun drawMiddle(drawScope: DrawScope) {
		drawScope.apply {
			drawLine(
				color = color,
				strokeWidth = strokeWidth,
				start = Offset(0f, y(size)),
				end = Offset(size.width, y(size)),
			)
		}
	}
}
