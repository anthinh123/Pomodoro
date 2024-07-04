package com.thinh.pomodoro.features.pomodoro.pomodoromanager

import com.thinh.pomodoro.features.pomodoro.data.WorkDay
import com.thinh.pomodoro.features.pomodoro.timer.TimeState
import com.thinh.pomodoro.features.pomodoro.timer.Timer
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage.BREAK
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage.LONG_BREAK
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage.WORK
import com.thinh.pomodoro.features.pomodoro.usecase.insert.InsertWorkDayUseCase
import com.thinh.pomodoro.features.settings.usecase.getsettings.GetSettingsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PomodoroManagerImpl(
    private val defaultDispatcher: CoroutineDispatcher,
    private val ioDispatcher: CoroutineDispatcher,
    private val timer: Timer,
    private val insertWorkDayUseCase: InsertWorkDayUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
) : PomodoroManager {

    private var pomodoroStage: PomodoroStage = WORK
    private var remainTime: Long = 0
    private var workDay: WorkDay? = null
    private var numberOfWorkings: Int = 0
    private var workTime: Long = WORK_TIME
    private var breakTime: Long = BREAK_TIME
    private var longBreakTime: Long = LONG_BREAK_TIME

    private val _podomoroUiState = MutableStateFlow(PodomoroUiState(remainTime = remainTime))
    override val podomoroUiState: StateFlow<PodomoroUiState> = _podomoroUiState

    init {
        startObserverTimer()
        loadSettings()
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

    private fun loadSettings() {
        CoroutineScope(ioDispatcher).launch {
            getSettingsUseCase.execute().collect { settings ->
                workTime = settings.workTime * 60L
                breakTime = settings.shortBreakTime * 60L
                longBreakTime = settings.longBreakTime * 60L

                if (_podomoroUiState.value.timeState == TimeState.INIT) {
                    timer.initTime(getPlayTime(pomodoroStage))
                }
            }
        }
    }

    private fun startObserverTimer() {
        CoroutineScope(defaultDispatcher).launch {
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
            WORK -> workTime
            BREAK -> breakTime
            LONG_BREAK -> longBreakTime
        }
    }

    private fun saveWorkDay() {
        CoroutineScope(ioDispatcher).launch {
            workDay?.let {
                workDay = it.copy(endTime = System.currentTimeMillis())
                insertWorkDayUseCase.execute(workDay!!)
                workDay = null
            }
        }
    }
}