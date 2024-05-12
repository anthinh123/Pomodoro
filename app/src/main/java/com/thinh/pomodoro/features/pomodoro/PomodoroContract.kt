package com.thinh.pomodoro.features.pomodoro

import com.thinh.podomoro.features.pomodoro.PomodoroStage
import com.thinh.pomodoro.features.pomodoro.PomodoroContract.PomodoroEvent
import com.thinh.pomodoro.features.pomodoro.PomodoroContract.PomodoroUiState
import com.thinh.podomoro.mvi.BaseMviContract

interface PomodoroContract : BaseMviContract<PomodoroUiState, PomodoroEvent> {
    data class PomodoroUiState(
        val pomodoroStage: PomodoroStage = PomodoroStage.WORK,
        val displayTime: String,
        val isRunning: Boolean,
        val numberOfWorking: Int = 0,
        val playRingtone: Boolean = false
    )

    sealed class PomodoroEvent {
        data object ButtonClick : PomodoroEvent()
        data object PlayedRingtone : PomodoroEvent()
    }

}