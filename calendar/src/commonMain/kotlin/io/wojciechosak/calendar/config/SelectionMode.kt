package io.wojciechosak.calendar.config

/**
 * Sealed class representing different selection modes for calendar dates.
 */
sealed class SelectionMode {
    /**
     * Object representing single selection mode.
     */
    data object Single : SelectionMode()

    /**
     * Data class representing multiple selection mode with an optional buffer size.
     *
     * @property bufferSize The size of the buffer for multiple selection mode. Default is 2.
     */
    data class Multiply(val bufferSize: Int = 2) : SelectionMode()

    /**
     * Data class representing multiple selection mode with an optional buffer size.
     *
     * @property bufferSize The size of the buffer for multiple selection mode. Default is 2.
     */
    data object Range : SelectionMode()
}
