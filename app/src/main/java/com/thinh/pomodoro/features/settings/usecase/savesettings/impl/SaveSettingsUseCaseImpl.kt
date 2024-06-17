package com.thinh.pomodoro.features.settings.usecase.savesettings.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.thinh.pomodoro.features.settings.data.AppSettings
import com.thinh.pomodoro.features.settings.data.AppSettingsKey
import com.thinh.pomodoro.features.settings.usecase.savesettings.SaveSettingsUseCase

class SaveSettingsUseCaseImpl(
    private val dataStore: DataStore<Preferences>,
) : SaveSettingsUseCase {
    override suspend fun execute(appSettings: AppSettings) {
        dataStore.edit { preferences ->
            preferences[AppSettingsKey.WORK_TIME] = appSettings.workTime
            preferences[AppSettingsKey.SHORT_BREAK_TIME] = appSettings.shortBreakTime
            preferences[AppSettingsKey.LONG_BREAK_TIME] = appSettings.longBreakTime
            preferences[AppSettingsKey.IS_DARK_MODE] = appSettings.isDarkMode
        }
    }
}