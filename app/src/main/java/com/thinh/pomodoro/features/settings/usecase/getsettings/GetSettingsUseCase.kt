package com.thinh.pomodoro.features.settings.usecase.getsettings

import com.thinh.pomodoro.features.settings.data.AppSettings
import kotlinx.coroutines.flow.Flow

interface GetSettingsUseCase {
    fun execute(): Flow<AppSettings>
}