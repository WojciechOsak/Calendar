package io.wojciechosak.calendar.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import java.awt.Toolkit

@Composable
actual fun getScreenSizeInfo(): ScreenSizeInfo {
	val screenSize = Toolkit.getDefaultToolkit().screenSize
	val density = LocalDensity.current
	return ScreenSizeInfo(
		heightPx = screenSize.height,
		widthPx = screenSize.width,
		widthDp = with(density) { screenSize.width.toDp() },
		heightDp = with(density) { screenSize.height.toDp() },
	)
}