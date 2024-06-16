package com.thinh.pomodoro.features.pomodoro.pomodoromanager

import com.thinh.pomodoro.features.pomodoro.timer.TimeState
import kotlinx.coroutines.flow.StateFlow

enum class PomodoroStage(val type: Int) {
    WORK(0), BREAK(1), LONG_BREAK(2)
}

const val WORK_TIME = 25 * 60L
const val BREAK_TIME = 5 * 60L
const val LONG_BREAK_TIME = 15 * 60L

interface PomodoroManager {
    val podomoroUiState: StateFlow<PodomoroUiState>

    fun takeActionToTimer()
    fun skipPomodoro()
    fun resetPomodoro()
    fun goToNextPomodoroStage()
}

data class PodomoroUiState(
    val pomodoroStage: PomodoroStage = PomodoroStage.WORK,
    val remainTime: Long,
    val timeState: TimeState = TimeState.INIT,
)
