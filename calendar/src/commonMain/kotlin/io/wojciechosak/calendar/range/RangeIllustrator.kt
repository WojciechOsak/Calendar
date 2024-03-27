package io.wojciechosak.calendar.range

import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Interface defining methods for illustrating date ranges.
 */
interface RangeIllustrator {

    /**
     * Method to draw the end of a date range.
     *
     * @param drawScope The drawing scope used for rendering.
     */
    fun drawEnd(drawScope: DrawScope)

    /**
     * Method to draw the start of a date range.
     *
     * @param drawScope The drawing scope used for rendering.
     */
    fun drawStart(drawScope: DrawScope)

    /**
     * Method to draw the middle part of a date range.
     *
     * @param drawScope The drawing scope used for rendering.
     */
    fun drawMiddle(drawScope: DrawScope)
}