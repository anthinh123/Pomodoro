package com.thinh.pomodoro.features.pomodoro.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroEvent
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroEvent.PlayPauseEvent
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroEvent.PlayedRingtone
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroEvent.ResetTime
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroEvent.SkipStage
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroUiState
import com.thinh.pomodoro.features.pomodoro.timer.TimeState
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroManager
import com.thinh.pomodoro.features.pomodoro.usecase.getnumberofworks.GetCountOfWorksInRangeUseCase
import com.thinh.pomodoro.mvi.BaseViewModel
import com.thinh.pomodoro.utils.TimeUtil
import kotlinx.coroutines.launch

class PodomoroViewModel(
    private val pomodoroManager: PomodoroManager,
    private val getCountOfWorksInRangeUseCase: GetCountOfWorksInRangeUseCase,
) : BaseViewModel<PomodoroUiState, PomodoroEvent>() {

    init {
        viewModelScope.launch {
            pomodoroManager.podomoroUiState.collect { uiState ->
                updateState {
                    copy(
                        pomodoroStage = uiState.pomodoroStage,
                        displayTime = convertMillisToTime(uiState.remainTime),
                        timeState = uiState.timeState,
                    )
                }

                if (uiState.timeState == TimeState.FINISHED) {
                    updateState {
                        copy(
                            playRingTone = true
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            getCountOfWorksInRangeUseCase.execute(
                pomodoroType = 0,
                startDate = TimeUtil.getStartTimeOfCurrentDay(),
                endDate = TimeUtil.getEndTimeOfCurrentDay()
            ).collect { numberOfWorking ->
                updateState {
                    copy(numberOfWorking = numberOfWorking)
                }
            }
        }
    }

    override fun createInitialState(): PomodoroUiState {
        return PomodoroUiState(
            displayTime = "25\n00",
            timeState = TimeState.INIT,
        )
    }

    override fun handleEvent(event: PomodoroEvent) {
        when (event) {
            PlayPauseEvent -> {
                playPause()
            }

            PlayedRingtone -> {
                updateState {
                    copy(playRingTone = false)
                }
            }

            SkipStage -> {
                pomodoroManager.skipPomodoro()
            }

            ResetTime -> {
                pomodoroManager.resetPomodoro()
            }
        }
    }

    private fun playPause() {
        pomodoroManager.takeActionToTimer()
    }

    private fun convertMillisToTime(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "${minutes.toString().padStart(2, '0')}\n${seconds.toString().padStart(2, '0')}"
    }

}