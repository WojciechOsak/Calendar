package io.wojciechosak.calendar.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator

@Composable
internal fun App() {
    Box(modifier = Modifier.safeContentPadding()) {
        Navigator(MenuScreen())
    }
}
