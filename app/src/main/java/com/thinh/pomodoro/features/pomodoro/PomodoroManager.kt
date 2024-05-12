package com.thinh.podomoro.features.pomodoro

import com.thinh.podomoro.features.pomodoro.PomodoroStage.BREAK
import com.thinh.podomoro.features.pomodoro.PomodoroStage.LONG_BREAK
import com.thinh.podomoro.features.pomodoro.PomodoroStage.WORK
import com.thinh.pomodoro.features.pomodoro.Timer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class PomodoroStage {
    WORK, BREAK, LONG_BREAK
}

enum class PodomoroState {
    INIT, PLAYING, PAUSED, FINISHED
}

const val WORK_TIME = 5 * 60L / 60
const val BREAK_TIME = 2 * 60L / 60
const val LONG_BREAK_TIME = 3 * 60L / 60

class PomodoroManager(
    private val timer: Timer,
) {
    private var pomodoroStage: PomodoroStage = WORK
    private var remainTime: Long = WORK_TIME
    private var state: PodomoroState = PodomoroState.INIT

    private val _podomoroUiState = MutableStateFlow(
        PodomoroUiState(remainTime = remainTime, isRunning = false)
    )
    val podomoroUiState: StateFlow<PodomoroUiState> = _podomoroUiState

    private var numberOfWorkings: Int = 0

    fun takeActionFromPlayPauseButton() {
        when (state) {
            PodomoroState.INIT -> {
                startObserverTimer()
                play()
            }

            PodomoroState.PLAYING -> {
                pause()
            }

            PodomoroState.PAUSED -> {
                resume()
            }

            PodomoroState.FINISHED -> {
                play()
            }
        }
    }

    private fun startObserverTimer() {
        GlobalScope.launch {
            timer.timerState.collect { timerState ->
                remainTime = timerState.remainTime
                _podomoroUiState.update {
                    it.copy(
                        remainTime = remainTime,
                        isRunning = timerState.isRunning,
                        isFinished = timerState.isFinished
                    )
                }

                if (timerState.isFinished) {
                    state = PodomoroState.FINISHED
                    goToNextPomodoroType()
                }
            }
        }
    }

    private fun goToNextPomodoroType() {
        when (pomodoroStage) {
            WORK -> {
                numberOfWorkings++
                pomodoroStage = if (numberOfWorkings % 4 == 0) LONG_BREAK else BREAK
            }

            BREAK -> pomodoroStage = WORK

            LONG_BREAK -> pomodoroStage = WORK

        }

        _podomoroUiState.update {
            it.copy(
                pomodoroStage = pomodoroStage,
                numberOfWorking = numberOfWorkings,
                isRunning = false,
                remainTime = getPlayTime(pomodoroStage)
            )
        }

    }

    private fun play() {
        state = PodomoroState.PLAYING
        timer.play(getPlayTime(pomodoroStage))
    }

    private fun pause() {
        state = PodomoroState.PAUSED
        timer.pause()
    }

    private fun resume() {
        state = PodomoroState.PLAYING
        timer.play(remainTime)
    }

    private fun getPlayTime(type: PomodoroStage) = when (type) {
        WORK -> WORK_TIME
        BREAK -> BREAK_TIME
        LONG_BREAK -> LONG_BREAK_TIME
    }
}

data class PodomoroUiState(
    val pomodoroStage: PomodoroStage = WORK,
    val remainTime: Long,
    val isRunning: Boolean,
    val isFinished: Boolean = false,
    val numberOfWorking: Int = 0,
)