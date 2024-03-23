package io.wojciechosak.calendar.config

sealed class SelectionMode {
    data object Single : SelectionMode()

    data class Multiply(val bufferSize: Int = 2) : SelectionMode()

    data object Range : SelectionMode()
}
