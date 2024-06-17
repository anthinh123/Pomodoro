package com.thinh.pomodoro.features.settings.usecase.savesettings

import com.thinh.pomodoro.features.settings.data.AppSettings

interface SaveSettingsUseCase {
    suspend fun execute(appSettings: AppSettings)
}