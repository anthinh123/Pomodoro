package com.thinh.pomodoro.features.settings.ui

import com.thinh.podomoro.mvi.BaseMviContract
import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingEvent
import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingUiState

interface SettingContract : BaseMviContract<SettingUiState, SettingEvent> {
    data class SettingUiState(
        val workTime: Int,
        val shortBreakTime: Int,
        val longBreakTime: Int,
        val isDarkMode: Boolean,
        val isSignOut: Boolean,
    )

    sealed class SettingEvent {
        data class WorkTimeChanged(val workTime: Int) : SettingEvent()
        data class ShortBreakTimeChanged(val shortBreakTime: Int) : SettingEvent()
        data class LongBreakTimeChanged(val longBreakTime: Int) : SettingEvent()
        data class IsDarkModeChanged(val isDarkMode: Boolean) : SettingEvent()
        data object SignOut : SettingEvent()
    }
}