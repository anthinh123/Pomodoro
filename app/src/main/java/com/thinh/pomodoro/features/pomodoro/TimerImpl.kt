package com.thinh.pomodoro.features.pomodoro

import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TimerImpl : Timer {

    private var timer: CountDownTimer? = null

    private val _timerState =
        MutableStateFlow(TimerState(remainTime = 0, isRunning = false, isFinished = false))
    override val timerState: StateFlow<TimerState> = _timerState

    override fun play(time: Long) {
        _timerState.update {
            it.copy(
                isRunning = true,
                isFinished = false
            )
        }
        timer = object : CountDownTimer(time * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timerState.update {
                    it.copy(remainTime = millisUntilFinished / 1000)
                }
            }

            override fun onFinish() {
                _timerState.update {
                    it.copy(
                        remainTime = 0,
                        isRunning = false,
                        isFinished = true
                    )
                }
            }
        }.start()
    }

    override fun pause() {
        timer?.cancel()
        _timerState.update {
            it.copy(
                isRunning = false,
                isFinished = false
            )
        }
    }

    override fun stop() {
        timer?.cancel()
        _timerState.update {
            it.copy(
                remainTime = 0,
                isRunning = false,
                isFinished = true
            )
        }
    }
}
