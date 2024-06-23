package com.thinh.pomodoro.features.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thinh.pomodoro.common.AppScaffold
import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingEvent.IsDarkModeChanged
import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingEvent.LongBreakTimeChanged
import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingEvent.ShortBreakTimeChanged
import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingEvent.WorkTimeChanged
import com.thinh.pomodoro.ui.theme.PomodoroColorScheme
import com.thinh.pomodoro.ui.theme.PomodoroTheme

@Composable
fun SettingScreen(
    uiState: SettingContract.SettingUiState,
    onEvent: (SettingContract.SettingEvent) -> Unit,
    updateDarkMode: (Boolean) -> Unit,
    onBack: () -> Unit,
) {

    var workTime by remember { mutableIntStateOf(uiState.workTime) }
    var shortBreakTime by remember { mutableIntStateOf(uiState.shortBreakTime) }
    var longBreakTime by remember { mutableIntStateOf(uiState.longBreakTime) }
//    var darkMode by remember { mutableStateOf(uiState.isDarkMode) }
//
//    LaunchedEffect(uiState.isDarkMode) {
//        darkMode = uiState.isDarkMode
//        updateDarkMode.invoke(uiState.isDarkMode)
//    }

    LaunchedEffect(uiState.workTime) {
        workTime = uiState.workTime
    }

    LaunchedEffect(uiState.shortBreakTime) {
        shortBreakTime = uiState.shortBreakTime
    }

    LaunchedEffect(uiState.longBreakTime) {
        longBreakTime = uiState.longBreakTime
    }

    AppScaffold(
        title = "Setting",
        isCanBack = true,
        onBackClicked = { onBack.invoke() }
    ) {

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
//            Row(
//                modifier = Modifier.padding(vertical = 8.dp),
//                verticalAlignment = CenterVertically
//            ) {
//                Text(
//                    text = "Dark mode",
//                    color = MaterialTheme.colorScheme.primary
//                )
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                Switch(
//                    checked = darkMode,
//                    onCheckedChange = {
//                        onEvent(IsDarkModeChanged(it))
//                    },
//                    colors = SwitchDefaults.colors(
//                        checkedThumbColor = MaterialTheme.colorScheme.primary,
//                        checkedTrackColor = MaterialTheme.colorScheme.secondary,
//                        checkedBorderColor = MaterialTheme.colorScheme.primary,
//                    )
//                )
//            }

            PomodoroSettingItem(
                title = "Work time",
                sliderValue = workTime,
                onSliderChanged = { workTime = it },
                onValueChangeFinished = {
                    onEvent(WorkTimeChanged(it))
                }
            )

            PomodoroSettingItem(
                title = "Short break time",
                sliderValue = shortBreakTime,
                onSliderChanged = { shortBreakTime = it },
                onValueChangeFinished = {
                    onEvent(ShortBreakTimeChanged(it))
                }
            )

            PomodoroSettingItem(
                title = "Long break time",
                sliderValue = longBreakTime,
                onSliderChanged = { longBreakTime = it },
                onValueChangeFinished = {
                    onEvent(LongBreakTimeChanged(it))
                }
            )
        }
    }

}

@Composable
private fun PomodoroSettingItem(
    title: String,
    sliderValue: Int,
    onSliderChanged: (Int) -> Unit,
    onValueChangeFinished: (Int) -> Unit,
) {
    var sliderPosition by remember { mutableFloatStateOf(sliderValue.toFloat()) }

    LaunchedEffect(sliderValue) {
        sliderPosition = sliderValue.toFloat()
    }
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {

        Row(verticalAlignment = CenterVertically) {

            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(text = "${sliderPosition.toInt()} min")
        }

        Slider(
            value = sliderValue.toFloat(),
            onValueChange = {
                sliderPosition = it
                onSliderChanged.invoke(it.toInt())
            },
            onValueChangeFinished = {
                onValueChangeFinished.invoke(sliderPosition.toInt())
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
            ),
            valueRange = 1f..60f,
        )
    }
}

@Preview(device = "spec:width=2640px,height=1080px,dpi=480,orientation=portrait")
@Composable
fun SettingScreenPreview() {
    PomodoroTheme(
        pomodoroColorScheme = PomodoroColorScheme.SHORT_BREAK_COLOR,
        darkMode = false
    ) {
        SettingScreen(
            uiState = SettingContract.SettingUiState(
                workTime = 25,
                shortBreakTime = 5,
                longBreakTime = 15,
                isDarkMode = false
            ),
            onEvent = {},
            updateDarkMode = {},
            onBack = {}
        )
    }
}