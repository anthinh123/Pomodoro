package com.thinh.pomodoro.ui.calendarlibrary.core

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

data class CalendarTheme(
    val backgroundColor: Color,
    val headerBackgroundColor: Color,
    val dayBackgroundColor: Color,
    val selectedDayBackgroundColor: Color,
    val dayValueTextColor: Color,
    val selectedDayValueTextColor: Color,
    val headerTextColor: Color,
    val weekDaysTextColor: Color,
    val dayShape: Shape
)

val calendarDefaultTheme: CalendarTheme
    @Composable
    @ReadOnlyComposable
    get() = CalendarTheme(
        backgroundColor = Color.Transparent,
        headerBackgroundColor = Color.Transparent,
        dayBackgroundColor = Color.Transparent,
        selectedDayBackgroundColor = MaterialTheme.colorScheme.primary,
        dayValueTextColor = MaterialTheme.colorScheme.onBackground,
        selectedDayValueTextColor = MaterialTheme.colorScheme.onBackground,
        headerTextColor = MaterialTheme.colorScheme.onBackground,
        weekDaysTextColor = MaterialTheme.colorScheme.onBackground,
        dayShape = RectangleShape

    )
