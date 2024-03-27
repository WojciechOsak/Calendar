# Compose Multiplatform Calendar

<div style="text-align:center">
    <img src="readme/baner.gif" style="display:block; margin:auto;">
</div>

[![Maven Central](https://img.shields.io/badge/dynamic/xml.svg?label=Maven%20Central&color=blue&url=https://repo1.maven.org/maven2/io/github/wojciechosak/calendar/maven-metadata.xml&query=(//metadata/versioning/versions/version)[not(contains(text(),%27-%27))][last()])](https://repo1.maven.org/maven2/io/github/wojciechosak/calendar/)


Highly configurable Kotlin Multiplatform Compose library where you can easily draw
calendar picker for any platform you want: Android, iOS, Desktop or even Web!

**We give you logic, you define UI with Composables!**

---

<div style="text-align:center">
    <img src="readme/info.png" style="display:block; margin:auto;">
</div>
<div style="text-align:center">
    <img src="readme/variants.png" style="display:block; margin:auto;">
</div>

Features:

|          Feature           | Supported |
|:--------------------------:|:---------:|
| Single month calendar view |     ✅     |
|       Week calendar        |     ✅     |
|    Horizontal calendar     |     ✅     |
|      Customizable UI       |     ✅     |
|      Headers support       |     ✅     |
|      Calendar offsets      |     ✅     |
|      Single selection      |     ✅     |
|     Multiple selection     |     ✅     |
|     Month/Year picker      |     ✅     |
|  Scroll to date animation  |     ✅     |
|     Vertical calendar      |     ✅     |
|      Range selection       |     ✅     |

---

# Platforms:

| Platform  | Supported |
|:---------:|:---------:|
|  Android  |     ✅     |
|    iOS    |     ✅     |
| Web(Wasm) |     ✅     |
|  Desktop  |     ✅     |

# Setup

[![Maven Central](https://img.shields.io/badge/dynamic/xml.svg?label=Maven%20Central&color=blue&url=https://repo1.maven.org/maven2/io/github/wojciechosak/calendar/maven-metadata.xml&query=(//metadata/versioning/versions/version)[not(contains(text(),%27-%27))][last()])](https://repo1.maven.org/maven2/io/github/wojciechosak/calendar/)

In Android project: 

```groovy
dependencies {
  implementation 'io.github.wojciechosak:calendar:<latest-version>'
}
```

In Kotlin Multiplatform project:

```groovy
commonMain.dependencies {
  implementation 'io.github.wojciechosak:calendar:<latest-version>'
}
```

```kotlin
CalendarView(
  config = rememberCalendarState(
    startDate = LocalDate.today(),
    monthOffset = 0
  ),
  day = { dayState ->
    // define your day composable here!
  }
)
```

# Composables

Simply use in Compose any view you want: 

|   View type    |                      Preview                       |
|:--------------:|:--------------------------------------------------:|
|  CalendarView  | <img src="readme/calendar-view.png" height="250"/> |
| HorizontalView |  <img src="readme/horizontal.gif" height="250"/>   |
|  VerticalView  |   <img src="readme/vertical.gif" height="250"/>    |
|    WeekView    |   <img src="readme/weekview.gif" height="250"/>    |
|  MonthPicker   |  <img src="readme/monthpicker.png" height="250"/>  |
|   YearPicker   |  <img src="readme/yearpicker.gif" height="250"/>   |

Each view get as parameter day cell composable. Thanks to that your calendar can look whatever you like:
<img src="readme/sample1.png" height="250"/>
<img src="readme/sample2.png" height="250"/>

## Range selection:

```kotlin
CalendarView(
  config = rememberCalendarState(
    startDate = startDate,
    monthOffset = monthOffset,
  ),
  selectionMode = SelectionMode.Range,
  rangeConfig = RangeConfig(rangeIllustrator = RoundedRangeIllustrator(Pallete.LightGreen)),
)
```

 <img src="readme/range.png" height="250"/> 

You can draw yourself implementations of RangeIllustrator, or use predefined `RoundedRangeIllustrator` 
and `UnderlineIllustrator`.

## Selection callbacks:

```kotlin
CalendarView(
  config = ...,
  onDateSelected = { date ->
    // date: List<LocalDate>
  },
  selectionMode = SelectionMode.Multiply(3) // SelectionMode.Single or SelectionMode.Range
)
```

---

# Sample project:

In directory sample you can find bunch of examples. You can run Android/iOS application and test it 
yourself. Below you can find instruction how to run sample project on your computer.

## Before running!

- check your system with [KDoctor](https://github.com/Kotlin/kdoctor)
- install JDK 17 on your machine

### Android

To run the application on android device/emulator:

- open project in Android Studio and run imported android run configuration

To build the application bundle:

- run `./gradlew :sample:composeApp:assembleDebug`
- find `.apk` file in `sample/composeApp/build/outputs/apk/debug/composeApp-debug.apk`

### Desktop

Run the desktop application: `./gradlew :sample:composeApp:run`

### iOS

To run the application on iPhone device/simulator:

- Open `iosApp/iosApp.xcproject` in Xcode and run standard configuration
- In XCode open Build Phases -> Run Script -> paste script:

```
    cd "$SRCROOT/../../"
    ./gradlew :sample:composeApp:embedAndSignAppleFrameworkForXcode
```

And build the project.

- After that you can
  use [Kotlin Multiplatform Mobile plugin](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform-mobile)
  for Android Studio

### Browser

Run the browser application: `./gradlew :sample:composeApp:jsBrowserDevelopmentRun`

