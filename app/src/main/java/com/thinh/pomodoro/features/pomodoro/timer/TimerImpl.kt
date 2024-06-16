package com.thinh.pomodoro.features.pomodoro.timer

import android.os.CountDownTimer
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TimerImpl : Timer {

    private var timer: CountDownTimer? = null

    private val _timerUiState = MutableStateFlow(TimerUiState(remainTime = 0))
    override val timerUiState: StateFlow<TimerUiState> = _timerUiState

    override fun initTime(time: Long) {
        _timerUiState.update {
            it.copy(
                remainTime = time,
                state = TimeState.INIT,
            )
        }
    }

    override fun play(time: Long) {
        _timerUiState.update {
            it.copy(
                state = TimeState.PLAYING,
            )
        }
        timer = createCountDownTimer(time).start()
    }

    private fun createCountDownTimer(time: Long) = object : CountDownTimer(time * 1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            Log.d("thinhav", "onTick: ${millisUntilFinished / 1000}")
            _timerUiState.update {
                it.copy(remainTime = millisUntilFinished / 1000)
            }
        }

        override fun onFinish() {
            _timerUiState.update {
                it.copy(
                    remainTime = 0,
                    state = TimeState.FINISHED
                )
            }
        }
    }

    override fun pause() {
        timer?.cancel()
        _timerUiState.update {
            it.copy(
                state = TimeState.PAUSED,
            )
        }
    }

    override fun stop() {
        timer?.cancel()
        _timerUiState.update {
            it.copy(
                state = TimeState.FINISHED
            )
        }
    }

    override fun reset() {
        timer?.cancel()
    }

}
