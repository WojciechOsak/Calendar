package io.wojciechosak.calendar.range

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

class RoundedRangeIllustrator(
    private val color: Color,
) : RangeIllustrator {
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

    override fun drawMiddle(drawScope: DrawScope) {
        drawScope.apply {
            drawRect(color = color)
        }
    }
}
