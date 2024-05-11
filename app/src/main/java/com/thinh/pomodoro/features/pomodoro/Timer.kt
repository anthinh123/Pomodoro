package com.thinh.pomodoro.features.pomodoro

import kotlinx.coroutines.flow.StateFlow

interface Timer {
    val timerState: StateFlow<TimerState>
    fun play(time: Long)
    fun pause()
    fun stop()
}

data class TimerState(
    val remainTime: Long,
    val isRunning: Boolean,
    val isFinished: Boolean = false
)