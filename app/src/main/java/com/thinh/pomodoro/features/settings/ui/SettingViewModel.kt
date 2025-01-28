package com.thinh.pomodoro.features.settings.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thinh.pomodoro.features.auth.SessionManager
import com.thinh.pomodoro.features.settings.data.AppSettings
import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingEvent
import com.thinh.pomodoro.features.settings.ui.SettingContract.SettingUiState
import com.thinh.pomodoro.features.settings.usecase.getsettings.GetSettingsUseCase
import com.thinh.pomodoro.features.settings.usecase.savesettings.SaveSettingsUseCase
import com.thinh.pomodoro.features.settings.usecase.savesettings.impl.SaveSettingsUseCaseImpl
import com.thinh.pomodoro.mvi.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val sessionManager: SessionManager,
) : BaseViewModel<SettingUiState, SettingEvent>() {

    override fun createInitialState(): SettingUiState {
        return SettingUiState(
            workTime = 0,
            shortBreakTime = 0,
            longBreakTime = 0,
            isDarkMode = false,
            isSignOut = false,
        )
    }

    init {
        Log.d("thinhav", "SettingViewModel sessionManager = " + sessionManager)
        getSettings()
    }

    override fun handleEvent(event: SettingEvent) {
        when (event) {
            is SettingEvent.WorkTimeChanged -> {
                updateState { copy(workTime = event.workTime) }
                saveSettings()
            }

            is SettingEvent.ShortBreakTimeChanged -> {
                updateState { copy(shortBreakTime = event.shortBreakTime) }
                saveSettings()
            }

            is SettingEvent.LongBreakTimeChanged -> {
                updateState { copy(longBreakTime = event.longBreakTime) }
                saveSettings()
            }

            is SettingEvent.IsDarkModeChanged -> {
                updateState { copy(isDarkMode = event.isDarkMode) }
                saveSettings()
            }

            SettingEvent.SignOut -> signOut()
        }
    }

    private fun signOut() {
        sessionManager.clearSession()
        updateState {
            copy(isSignOut = true)
        }
    }

    private fun saveSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            saveSettingsUseCase.execute(
                AppSettings(
                    workTime = uiState.value.workTime,
                    shortBreakTime = uiState.value.shortBreakTime,
                    longBreakTime = uiState.value.longBreakTime,
                    isDarkMode = uiState.value.isDarkMode
                )
            )
        }
    }

    private fun getSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            getSettingsUseCase.execute().collect { appSettings ->
                Log.d("SettingViewModel", "getSettings: $appSettings")
                updateState {
                    copy(
                        workTime = appSettings.workTime,
                        shortBreakTime = appSettings.shortBreakTime,
                        longBreakTime = appSettings.longBreakTime,
                        isDarkMode = appSettings.isDarkMode
                    )
                }
            }
        }
    }


}