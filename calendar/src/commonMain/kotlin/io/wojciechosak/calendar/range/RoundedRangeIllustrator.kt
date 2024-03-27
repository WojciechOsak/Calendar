package io.wojciechosak.calendar.range

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Class implementing the RangeIllustrator interface to illustrate date ranges with rounded corners.
 *
 * @param color The color used for illustrating the date range.
 */
class RoundedRangeIllustrator(
    private val color: Color,
) : RangeIllustrator {

    /**
     * Method to draw the end of a date range with rounded corners.
     *
     * @param drawScope The drawing scope used for rendering.
     */
    override fun drawEnd(drawScope: DrawScope) {
        drawScope.apply {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 180f,
                useCenter = true,
            )
            drawRect(
                color = color,
                size = size.copy(width = size.width * 0.5f),
            )
        }
    }

    /**
     * Method to draw the start of a date range with rounded corners.
     *
     * @param drawScope The drawing scope used for rendering.
     */
    override fun drawStart(drawScope: DrawScope) {
        drawScope.apply {
            drawArc(
                color = color,
                startAngle = 180f,
                sweepAngle = 360f,
                useCenter = true,
            )
            drawRect(
                color = color,
                size = size.copy(width = size.width * 0.5f),
                topLeft = Offset(size.width * 0.5f, 0f),
            )
        }
    }

    /**
     * Method to draw the middle part of a date range with rounded corners.
     *
     * @param drawScope The drawing scope used for rendering.
     */
    override fun drawMiddle(drawScope: DrawScope) {
        drawScope.apply {
            drawRect(color = color)
        }
    }
}
