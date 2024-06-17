package com.thinh.pomodoro.features.settings.data

data class AppSettings(
    val workTime: Int,
    val shortBreakTime: Int,
    val longBreakTime: Int,
    val isDarkMode: Boolean,
)
