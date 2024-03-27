package io.wojciechosak.calendar.range

import androidx.compose.ui.graphics.Color
import io.wojciechosak.calendar.utils.Pallete.LightBlue

/**
 * Data class representing the configuration for illustrating date ranges.
 *
 * @property color The color used for illustrating the date range. Default is LightBlue.
 * @property rangeIllustrator The range illustrator used for rendering the date range. Default is a RoundedRangeIllustrator.
 */
data class RangeConfig(
    val color: Color = LightBlue,
    val rangeIllustrator: RangeIllustrator = RoundedRangeIllustrator(color),
)
