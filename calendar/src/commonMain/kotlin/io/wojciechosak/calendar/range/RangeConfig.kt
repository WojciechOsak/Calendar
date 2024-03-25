package io.wojciechosak.calendar.range

import androidx.compose.ui.graphics.Color
import io.wojciechosak.calendar.utils.Pallete.LightBlue

data class RangeConfig(
    val color: Color = LightBlue,
    val rangeIllustrator: RangeIllustrator = RoundedRangeIllustrator(color),
)
