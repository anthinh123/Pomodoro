package com.thinh.pomodoro.features.analytics.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thinh.pomodoro.common.AppScaffold
import com.thinh.pomodoro.features.analytics.ui.PomodoroAnalyticsContract.PomodoroAnalyticsEvent
import com.thinh.pomodoro.features.analytics.ui.PomodoroAnalyticsContract.PomodoroAnalyticsUiState
import com.thinh.pomodoro.features.analytics.ui.chart.ChartCirclePie
import com.thinh.pomodoro.features.analytics.ui.chart.ChartModel
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage
import com.thinh.pomodoro.ui.calendarlibrary.ExpandableCalendar
import com.thinh.pomodoro.ui.calendarlibrary.core.calendarDefaultTheme
import com.thinh.pomodoro.ui.theme.PomodoroColorScheme
import com.thinh.pomodoro.ui.theme.PomodoroTheme
import com.thinh.pomodoro.ui.theme.long_break_chart_dark
import com.thinh.pomodoro.ui.theme.long_break_chart_light
import com.thinh.pomodoro.ui.theme.short_break_chart_dark
import com.thinh.pomodoro.ui.theme.short_break_chart_light
import com.thinh.pomodoro.ui.theme.working_chart_dark
import com.thinh.pomodoro.ui.theme.working_chart_light
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PomodoroAnalyticsScreen(
    uiState: PomodoroAnalyticsUiState,
    onEvent: (PomodoroAnalyticsEvent) -> Unit,
    isDarkMode: Boolean,
    onBack: () -> Unit,
) {

    var chartModels by remember { mutableStateOf<List<ChartModel>>(emptyList()) }
    var text by remember { mutableStateOf("") }
    var totalTime by remember { mutableIntStateOf(0) }

    LaunchedEffect(uiState) {
        Log.d("PomodoroAnalyticsScreen", "uiState: $uiState")
        chartModels = listOf(
            ChartModel(
                value = uiState.workTime.toFloat(),
                color = getChartColor(isDarkMode, PomodoroStage.WORK)
            ),
            ChartModel(
                value = uiState.breakTime.toFloat(),
                color = getChartColor(isDarkMode, PomodoroStage.BREAK)
            ),
            ChartModel(
                value = uiState.longBreakTime.toFloat(),
                color = getChartColor(isDarkMode, PomodoroStage.LONG_BREAK)
            )
        )
        totalTime = uiState.workTime + uiState.breakTime + uiState.longBreakTime
        text = "Work time: ${uiState.workTime} minutes"
    }

    AppScaffold(
        title = "Analytics",
        isCanBack = true,
        onBackClicked = { onBack.invoke() }
    ) {

        ExpandableCalendar(theme = calendarDefaultTheme.copy(
            headerTextColor = MaterialTheme.colorScheme.onBackground,
            dayValueTextColor = MaterialTheme.colorScheme.onBackground,
            weekDaysTextColor = MaterialTheme.colorScheme.onBackground,
            selectedDayBackgroundColor = MaterialTheme.colorScheme.tertiary,
            selectedDayValueTextColor = MaterialTheme.colorScheme.primary,
            dayShape = CircleShape,
        ), onDayClick = {
            Log.d("PomodoroAnalyticsScreen", "onDayClick: $it")
        })

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            ChartCirclePie(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(),
                charts = chartModels,
                text = text,
                totalTime = totalTime
            )

            Spacer(modifier = Modifier.height(16.dp))

            ChartLine(isDarkMode, PomodoroStage.WORK, "Work", uiState.workTime)
            ChartLine(isDarkMode, PomodoroStage.BREAK, "Break", uiState.breakTime)
            ChartLine(isDarkMode, PomodoroStage.LONG_BREAK, "Long break", uiState.longBreakTime)
        }
    }

}

@Composable
private fun ChartLine(isDarkMode: Boolean, type: PomodoroStage, title: String, time: Int) {
    Row(
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(getChartColor(isDarkMode, type))
        )

        Text(
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "$time min",
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

private fun getChartColor(isDarkMode: Boolean, type: PomodoroStage): Color {
    return if (isDarkMode) {
        when (type) {
            PomodoroStage.WORK -> working_chart_dark
            PomodoroStage.BREAK -> short_break_chart_dark
            PomodoroStage.LONG_BREAK -> long_break_chart_dark
        }
    } else {
        when (type) {
            PomodoroStage.WORK -> working_chart_light
            PomodoroStage.BREAK -> short_break_chart_light
            PomodoroStage.LONG_BREAK -> long_break_chart_light
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PomodoroAnalyticsScreenPreview() {
    PomodoroTheme(
        pomodoroColorScheme = PomodoroColorScheme.SHORT_BREAK_COLOR,
        darkMode = false
    ) {
        PomodoroAnalyticsScreen(
            uiState = PomodoroAnalyticsUiState(),
            onEvent = {},
            isDarkMode = false,
            onBack = {}
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PomodoroAnalyticsScreenPreview2() {
    PomodoroTheme(
        pomodoroColorScheme = PomodoroColorScheme.WORKING_COLOR,
        darkMode = false
    ) {
        PomodoroAnalyticsScreen(
            uiState = PomodoroAnalyticsUiState(),
            onEvent = {},
            isDarkMode = false,
            onBack = {}
        )
    }
}