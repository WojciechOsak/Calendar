package io.wojciechosak.calendar.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.wojciechosak.calendar.config.DayState
import io.wojciechosak.calendar.utils.Pallete

@Composable
fun CalendarDay(
    state: DayState,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    secondRowText: String = "",
) = with(state) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.size(50.dp).padding(0.dp),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(1.dp, Color.Transparent),
        contentPadding = PaddingValues(0.dp),
        interactionSource = interactionSource,
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "${date.dayOfMonth}",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )

            if (secondRowText.isNotEmpty()) {
                Text(
                    secondRowText,
                    color = Color.Gray,
                    fontSize = 10.sp,
                    modifier =
                        Modifier.fillMaxWidth()
                            .background(if (isActiveDay) Pallete.LightGreen else Color(0xefefef)),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
