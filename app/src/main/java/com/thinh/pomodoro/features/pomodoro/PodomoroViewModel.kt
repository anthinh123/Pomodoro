package com.thinh.podomoro.features.podomoro

import androidx.lifecycle.viewModelScope
import com.thinh.podomoro.features.podomoro.PomodoroContract.PomodoroEvent
import com.thinh.podomoro.features.podomoro.PomodoroContract.PomodoroEvent.*
import com.thinh.podomoro.features.podomoro.PomodoroContract.PomodoroUiState
import com.thinh.podomoro.mvi.BaseViewModel
import kotlinx.coroutines.launch

class PodomoroViewModel(
    private val pomodoroManager: PomodoroManager
) : BaseViewModel<PomodoroUiState, PomodoroEvent>() {

    init {
        viewModelScope.launch {
            pomodoroManager.podomoroUiState.collect {
                val displayTime: String = convertMillisToTime(it.remainTime)
                updateState {
                    copy(
                        displayTime = displayTime,
                        isRunning = it.isRunning,
                        playRingtone = it.isFinished,
                        numberOfWorking = it.numberOfWorking
                    )
                }
            }
        }
    }

    override fun createInitialState(): PomodoroUiState {
        return PomodoroUiState(
            displayTime = "25:00",
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
        return "${minutes.toString().padStart(2, '0')} : ${seconds.toString().padStart(2, '0')}"
    }
}