package com.thinh.pomodoro.features.pomodoro.pomodoromanager

import kotlinx.coroutines.flow.StateFlow


enum class PomodoroStage(val type: Int) {
    WORK(0), BREAK(1), LONG_BREAK(2)
}

const val WORK_TIME = 1 * 10L
const val BREAK_TIME = 1 * 10L
const val LONG_BREAK_TIME = 1 * 10L

interface PomodoroManager {
    val podomoroUiState: StateFlow<PodomoroUiState>

    fun takeActionToTimer()
    fun skipPomodoro()
    fun resetPomodoro()
}
