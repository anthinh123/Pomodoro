package com.thinh.podomoro.features.podomoro

import com.thinh.podomoro.features.podomoro.PomodoroContract.PomodoroEvent
import com.thinh.podomoro.features.podomoro.PomodoroContract.PomodoroUiState
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