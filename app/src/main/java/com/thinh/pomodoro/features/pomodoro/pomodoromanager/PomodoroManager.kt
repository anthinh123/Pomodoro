package com.thinh.pomodoro.features.pomodoro.pomodoromanager

import kotlinx.coroutines.flow.StateFlow


enum class PomodoroStage {
    WORK, BREAK, LONG_BREAK
}

const val WORK_TIME = 25 * 60L
const val BREAK_TIME = 5 * 60L
const val LONG_BREAK_TIME = 15 * 60L

interface PomodoroManager {
    val podomoroUiState: StateFlow<PodomoroUiState>

    fun takeActionToTimer()
    fun skipPomodoro()
    fun resetPomodoro()
}
