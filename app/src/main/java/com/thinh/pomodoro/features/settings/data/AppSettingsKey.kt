package com.thinh.pomodoro.features.settings.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey

object AppSettingsKey {
    val WORK_TIME = intPreferencesKey("work_time")
    val SHORT_BREAK_TIME = intPreferencesKey("short_break_time")
    val LONG_BREAK_TIME = intPreferencesKey("long_break_time")
    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
}