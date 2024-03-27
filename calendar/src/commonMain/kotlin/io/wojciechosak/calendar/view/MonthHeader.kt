package io.wojciechosak.calendar.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Month

/**
 * Default Month header used to display month as text.
 */
@Composable
fun MonthHeader(
    month: Month,
    year: Int,
) {
    val months =
        listOf(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December",
        )
    Text(
        "${months.getOrNull(month.ordinal)} $year",
        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp, top = 0.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 16.sp,
    )
}
