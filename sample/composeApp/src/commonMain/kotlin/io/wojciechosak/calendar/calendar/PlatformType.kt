package io.wojciechosak.calendar.calendar

enum class PlatformType {
	MOBILE,
	WEB,
	DESKTOP
}

expect fun getPlatformType(): PlatformType