package com.thinh.pomodoro.features.pomodoro.pomodoromanager

import com.thinh.pomodoro.features.pomodoro.timer.TimeState
import com.thinh.pomodoro.features.pomodoro.timer.Timer
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage.BREAK
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage.LONG_BREAK
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage.WORK
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class PomodoroManagerImpl(
    private val timer: Timer,
) : PomodoroManager {
    private var pomodoroStage: PomodoroStage = WORK
    private var remainTime: Long = 0
    private var timeState: TimeState = TimeState.INIT

    private val _podomoroUiState = MutableStateFlow(PodomoroUiState(remainTime = remainTime))
    override val podomoroUiState: StateFlow<PodomoroUiState> = _podomoroUiState

    private var numberOfWorkings: Int = 0

    init {
        timer.initTime(getPlayTime(pomodoroStage))
        startObserverTimer()
    }

    override fun takeActionToTimer() {
        when (timeState) {
            TimeState.INIT -> {
                play()
            }

            TimeState.PLAYING -> {
                pause()
            }

            TimeState.PAUSED -> {
                resume()
            }

            TimeState.FINISHED -> {
                play()
            }
        }
    }

    override fun skipPomodoro() {
        timer.stop()
    }

    override fun resetPomodoro() {
        timer.reset()
        timeState = TimeState.INIT
        timer.initTime(getPlayTime(pomodoroStage))
    }

    private fun startObserverTimer() {
        GlobalScope.launch {
            timer.timerUiState.collect { timerState ->
                remainTime = timerState.remainTime
                _podomoroUiState.update {
                    it.copy(
                        remainTime = remainTime,
                        timeState = timerState.state
                    )
                }

                if (timerState.state == TimeState.FINISHED) {
                    timeState = TimeState.INIT
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

        timer.initTime(getPlayTime(pomodoroStage))

        _podomoroUiState.update {
            it.copy(
                pomodoroStage = pomodoroStage,
                numberOfWorking = numberOfWorkings,
            )
        }
    }

    private fun play() {
        timeState = TimeState.PLAYING
        timer.play(getPlayTime(pomodoroStage))
    }

    private fun pause() {
        timeState = TimeState.PAUSED
        timer.pause()
    }

    private fun resume() {
        timeState = TimeState.PLAYING
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
    val timeState: TimeState = TimeState.INIT,
    val numberOfWorking: Int = 0,
)