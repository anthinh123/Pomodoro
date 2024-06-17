package com.thinh.pomodoro.features.settings.usecase.getsettings.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.thinh.pomodoro.features.settings.data.AppSettings
import com.thinh.pomodoro.features.settings.data.AppSettingsKey
import com.thinh.pomodoro.features.settings.usecase.getsettings.GetSettingsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetSettingsUseCaseImpl(
    private val dataStore: DataStore<Preferences>,
) : GetSettingsUseCase {
    override fun execute(): Flow<AppSettings> {
        return dataStore.data.map { preferences ->
            val workTime = preferences[AppSettingsKey.WORK_TIME] ?: 25
            val shortBreakTime = preferences[AppSettingsKey.SHORT_BREAK_TIME] ?: 5
            val longBreakTime = preferences[AppSettingsKey.LONG_BREAK_TIME] ?: 15
            val isDarkMode: Boolean = preferences[AppSettingsKey.IS_DARK_MODE] ?: false

            AppSettings(workTime, shortBreakTime, longBreakTime, isDarkMode)
        }
    }
}