package com.thinh.podomoro.features.pomodoro

import androidx.lifecycle.viewModelScope
import com.thinh.pomodoro.features.pomodoro.PomodoroContract.PomodoroEvent
import com.thinh.pomodoro.features.pomodoro.PomodoroContract.PomodoroEvent.*
import com.thinh.pomodoro.features.pomodoro.PomodoroContract.PomodoroUiState
import com.thinh.podomoro.mvi.BaseViewModel
import kotlinx.coroutines.launch

class PodomoroViewModel(
    private val pomodoroManager: PomodoroManager
) : BaseViewModel<PomodoroUiState, PomodoroEvent>() {

    init {
        viewModelScope.launch {
            pomodoroManager.podomoroUiState.collect { uiState ->
                updateState {
                    copy(
                        pomodoroStage = uiState.pomodoroStage,
                        displayTime = convertMillisToTime(uiState.remainTime),
                        isRunning = uiState.isRunning,
                        numberOfWorking = uiState.numberOfWorking,
                        playRingtone = uiState.isFinished
                    )
                }
            }
        }
    }

    override fun createInitialState(): PomodoroUiState {
        return PomodoroUiState(
            displayTime = "25\n00",
            isRunning = false,
        )
    }

    override fun handleEvent(event: PomodoroEvent) {
        when (event) {
            ButtonClick -> {
                buttonClick()
            }

            PlayedRingtone -> {
                updateState {
                    copy(playRingtone = false)
                }
            }

        }
    }

    private fun buttonClick() {
        pomodoroManager.takeActionFromPlayPauseButton()
    }

    private fun convertMillisToTime(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "${minutes.toString().padStart(2, '0')}\n${seconds.toString().padStart(2, '0')}"
    }
}