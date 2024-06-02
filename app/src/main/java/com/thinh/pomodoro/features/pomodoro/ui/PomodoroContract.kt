package com.thinh.pomodoro.features.pomodoro.ui

import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroEvent
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroUiState
import com.thinh.podomoro.mvi.BaseMviContract
import com.thinh.pomodoro.features.pomodoro.timer.TimeState
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage

interface PomodoroContract : BaseMviContract<PomodoroUiState, PomodoroEvent> {
    data class PomodoroUiState(
        val pomodoroStage: PomodoroStage = PomodoroStage.WORK,
        val displayTime: String,
        val timeState: TimeState,
        val numberOfWorking: Int = 0,
    )

    sealed class PomodoroEvent {
        data object PlayPauseEvent : PomodoroEvent()
        data object PlayedRingtone : PomodoroEvent()
        data object SkipStage : PomodoroEvent()
        data object ResetTime : PomodoroEvent()
    }

}