package com.thinh.pomodoro.features.pomodoro.timer

import kotlinx.coroutines.flow.StateFlow

interface Timer {
    val timerUiState: StateFlow<TimerUiState>
    fun initTime(time: Long)
    fun play(time: Long)
    fun pause()
    fun stop()
    fun reset()
}

data class TimerUiState(
    val remainTime: Long,
    val state: TimeState = TimeState.INIT,
)

enum class TimeState {
    INIT, PLAYING, PAUSED, FINISHED
}