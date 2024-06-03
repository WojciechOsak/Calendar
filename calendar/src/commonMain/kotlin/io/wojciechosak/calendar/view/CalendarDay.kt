package io.wojciechosak.calendar.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.config.DayState
import io.wojciechosak.calendar.utils.Pallete

/**
 * Composable function to display a calendar day.
 *
 * @param state The state of the day.
 * @param interactionSource The interaction source for the view.
 * @param onClick The callback to be invoked when the view is clicked.
 * @param modifier The modifier for the view.
 */
@Composable
fun CalendarDay(
	state: DayState,
	interactionSource: MutableInteractionSource = MutableInteractionSource(),
	onClick: () -> Unit = {},
	modifier: Modifier = Modifier,
) = with(state) {
	Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
		OutlinedButton(
			onClick = onClick,
			modifier = modifier,
			shape = RoundedCornerShape(50.dp),
			border = BorderStroke(1.dp, Color.Transparent),
			contentPadding = PaddingValues(0.dp),
			interactionSource = remember { interactionSource },
			enabled = enabled,
			colors =
			ButtonDefaults.outlinedButtonColors(
				contentColor =
				if (isForPreviousMonth || isForNextMonth) {
					Color.LightGray
				} else {
					if (isActiveDay) Pallete.LightGreen else Pallete.LightBlue
				},
			),
		) {
			Text(
				"${date.dayOfMonth}",
				fontSize = 20.sp,
				textAlign = TextAlign.Center,
			)
		}
	}
}
