package io.wojciechosak.calendar.config

object CalendarConstants {
	// Compose 1.6.1 bug: https://issuetracker.google.com/issues/311414925, let's use fixed numbers for now.
	// internal const val MAX_PAGES = Int.MAX_VALUE
	internal const val MAX_PAGES = 100000
	internal const val INITIAL_PAGE_INDEX = MAX_PAGES / 2
}
