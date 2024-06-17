package com.thinh.pomodoro.features.settings.ui

import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingEvent
import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingUiState
import com.thinh.pomodoro.mvi.BaseViewModel

class SettingViewModel(

) : BaseViewModel<SettingUiState, SettingEvent>(){
    override fun createInitialState(): SettingUiState {
        return SettingUiState(
            workTime = 0,
            shortBreakTime = 0,
            longBreakTime = 0,
            isDarkMode = false
        )
    }

    override fun handleEvent(event: SettingEvent) {
        when(event){
            is SettingEvent.WorkTimeChanged -> {
            }
            is SettingEvent.ShortBreakTimeChanged -> {
            }
            is SettingEvent.LongBreakTimeChanged -> {
            }
            is SettingEvent.IsDarkModeChanged -> {
            }
        }
    }


}