package io.wojciechosak.calendar.range

import androidx.compose.ui.graphics.drawscope.DrawScope

interface RangeIllustrator {
    fun drawEnd(drawScope: DrawScope)

    fun drawStart(drawScope: DrawScope)

    fun drawMiddle(drawScope: DrawScope)
}
