package com.thinh.pomodoro.ui.calendarlibrary.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ToggleExpandCalendarButton(
    isExpanded: Boolean,
    expand: () -> Unit,
    collapse: () -> Unit,
    color: Color
) {
    IconToggleButton(
        checked = isExpanded,
        onCheckedChange = { isChecked -> if (isChecked) expand() else collapse() }
    ) {
        if (isExpanded) {
            Icon(Icons.Default.KeyboardArrowUp, "Collapse calendar", tint = color)
        } else {
            Icon(Icons.Default.KeyboardArrowDown, "Expand calendar", tint = color)
        }
    }
}