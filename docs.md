# Composable Functions Documentation

## HorizontalCalendarView

Composable function to display a horizontal calendar view.

### Parameters:

- `startDate`: The start date of the calendar.
- `pagerState`: The PagerState used to control the horizontal paging behavior of the calendar.
- `modifier`: The modifier for styling and layout of the calendar.
- `pageSize`: The size of each page in the calendar. Default is `PageSize.Fill`.
- `beyondBoundsPageCount`: The number of pages to keep loaded beyond the visible bounds. Default is
  3.
- `contentPadding`: The padding applied around the content of the calendar.
- `calendarAnimator`: The animator used for animating calendar transitions.
- `calendarView`: The composable function to display the content of each calendar page.

## MonthPicker

Composable function to display a month picker with selectable months.

### Parameters:

- `columns`: The number of columns in the grid layout of the month picker. Default is 4.
- `horizontalArrangement`: The horizontal alignment of items in the grid layout. Default
  is `Alignment.CenterHorizontally`.
- `verticalArrangement`: The vertical arrangement of items in the grid layout. Default
  is `Arrangement.Center`.
- `modifier`: The modifier for styling and layout of the month picker.
- `userScrollEnabled`: Whether scrolling is enabled for the month picker. Default is true.
- `monthCount`: The total number of months to display in the picker. Default is 12.
- `onMonthSelected`: The callback invoked when a month is selected.
- `monthView`: The composable function to display each month item in the picker.

## VerticalCalendarView

Composable function to display a vertical calendar view.

### Parameters:

- `startDate`: The start date of the calendar.
- `calendarAnimator`: The animator used for animating calendar transitions.
- `modifier`: The modifier for styling and layout of the calendar.
- `pageSize`: The size of each page in the calendar. Default is `PageSize.Fill`.
- `contentPadding`: The padding applied around the content of the calendar.
- `beyondBoundsPageCount`: The number of pages to keep loaded beyond the visible bounds. Default is
  3.
- `calendarView`: The composable function to display the content of each calendar page.

## WeekView

Composable function to display a week view with selectable days.

### Parameters:

- `startDate`: The start date of the week view. Default is the current date.
- `minDate`: The minimum selectable date in the week view. Default is three months before the start
  date.
- `maxDate`: The maximum selectable date in the week view. Default is three months after the end
  date of the month containing the start date.
- `daysOffset`: The offset in days from the start date. Default is 0.
- `showDaysBesideRange`: Whether to show days beside the range. Default is true.
- `calendarAnimator`: The animator used for animating calendar transitions.
- `isActive`: A lambda function to determine if a date is considered active. Default is comparison
  with the current date.
- `modifier`: The modifier for styling and layout of the week view.
- `firstVisibleDate`: A callback invoked with the first visible date in the week view.
- `day`: The composable function to display each day item in the week view.

## YearPicker

Composable function to display a year picker with selectable years.

### Parameters:

- `columns`: The number of columns in the grid layout of the year picker. Default is 4.
- `rows`: The number of rows in the grid layout of the year picker. Default is 3.
- `startDate`: The start date of the year picker. Default is the current date.
- `mode`: The mode of the year picker. Default is `YearPickerMode.HORIZONTAL`.
- `yearOffset`: The offset in years from the start date. Default is 0.
- `modifier`: The modifier for styling and layout of the year picker.
- `pageSize`: The size of each page in the year picker. Default is `PageSize.Fill`.
- `onYearSelected`: The callback invoked when a year is selected.
- `yearView`: The composable function to display each year item in the picker.

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

You can draw yourself implementations of RangeIllustrator, or use
predefined `RoundedRangeIllustrator`
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
