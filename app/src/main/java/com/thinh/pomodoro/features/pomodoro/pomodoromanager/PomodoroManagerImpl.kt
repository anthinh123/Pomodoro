package com.thinh.pomodoro.features.pomodoro.pomodoromanager

import com.thinh.pomodoro.features.pomodoro.data.WorkDay
import com.thinh.pomodoro.features.pomodoro.timer.TimeState
import com.thinh.pomodoro.features.pomodoro.timer.Timer
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage.BREAK
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage.LONG_BREAK
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage.WORK
import com.thinh.pomodoro.features.pomodoro.usecase.insert.InsertWorkDayUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PomodoroManagerImpl(
    private val timer: Timer,
    private val insertWorkDayUseCase: InsertWorkDayUseCase,
) : PomodoroManager {
    private var pomodoroStage: PomodoroStage = WORK
    private var remainTime: Long = 0
    private var workDay: WorkDay? = null

    private val _podomoroUiState = MutableStateFlow(PodomoroUiState(remainTime = remainTime))
    override val podomoroUiState: StateFlow<PodomoroUiState> = _podomoroUiState

    private var numberOfWorkings: Int = 0

    init {
        timer.initTime(getPlayTime(pomodoroStage))
        startObserverTimer()
    }

    override fun takeActionToTimer() {
        when (_podomoroUiState.value.timeState) {
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
        timer.initTime(getPlayTime(pomodoroStage))
    }

    override fun goToNextPomodoroStage() {
        when (pomodoroStage) {
            WORK -> {
                numberOfWorkings++
                pomodoroStage = if (numberOfWorkings % 4 == 0) LONG_BREAK else BREAK
            }

            BREAK -> pomodoroStage = WORK

            LONG_BREAK -> pomodoroStage = WORK
        }
        timer.initTime(getPlayTime(pomodoroStage))
    }

    private fun startObserverTimer() {
        GlobalScope.launch {
            timer.timerUiState.collect { timerState ->
                remainTime = timerState.remainTime
                _podomoroUiState.update {
                    it.copy(
                        remainTime = remainTime,
                        timeState = timerState.state,
                        pomodoroStage = pomodoroStage,
                    )
                }

                if (timerState.state == TimeState.FINISHED) {
                    saveWorkDay()
                }
            }
        }
    }

    private fun play() {
        timer.play(getPlayTime(pomodoroStage))
        workDay = WorkDay(
            date = System.currentTimeMillis(),
            typeOfPomodoro = pomodoroStage.type,
            startTime = System.currentTimeMillis(),
            endTime = 0
        )
    }

    private fun pause() {
        timer.pause()
    }

    private fun resume() {
        timer.play(remainTime)
    }

    private fun getPlayTime(type: PomodoroStage): Long {
        return when (type) {
            WORK -> WORK_TIME
            BREAK -> BREAK_TIME
            LONG_BREAK -> LONG_BREAK_TIME
        }
    }

    private fun saveWorkDay() {
        GlobalScope.launch {
            workDay?.let {
                workDay = it.copy(endTime = System.currentTimeMillis())
                insertWorkDayUseCase.execute(workDay!!)
                workDay = null
            }
        }
    }
}

data class PodomoroUiState(
    val pomodoroStage: PomodoroStage = WORK,
    val remainTime: Long,
    val timeState: TimeState = TimeState.INIT,
)