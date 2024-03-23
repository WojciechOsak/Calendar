package io.wojciechosak.calendar.modifiers

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun Modifier.passTouchGesture(onTouchEvent: () -> Unit): Modifier =
    composed {
        pointerInput(Unit) {
            awaitEachGesture {
                awaitFirstDown(requireUnconsumed = false)
                val change = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                change?.let { onTouchEvent() }
            }
        }
    }
