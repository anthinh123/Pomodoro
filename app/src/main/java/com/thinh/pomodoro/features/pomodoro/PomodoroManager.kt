package com.thinh.podomoro.features.podomoro

import com.thinh.podomoro.features.podomoro.PomodoroType.BREAK
import com.thinh.podomoro.features.podomoro.PomodoroType.LONG_BREAK
import com.thinh.podomoro.features.podomoro.PomodoroType.WORK
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class PomodoroType() {
    WORK, BREAK, LONG_BREAK
}

enum class PodomoroState {
    INIT, PLAYING, PAUSED, FINISHED
}

const val WORK_TIME = 5 * 60L
const val BREAK_TIME = 1 * 60L / 60
const val LONG_BREAK_TIME = 2 * 60L / 60

@OptIn(DelicateCoroutinesApi::class)
class PomodoroManager {
    private var pomodoroType: PomodoroType = WORK
    private var remainTime: Long = WORK_TIME
    private var state: PodomoroState = PodomoroState.INIT

    private val _podomoroUiState = MutableStateFlow(
        PodomoroUiState(remainTime = remainTime, isRunning = false)
    )
    val podomoroUiState: StateFlow<PodomoroUiState> = _podomoroUiState

    private var job: Job? = null
    private var numberOfWorkings: Int = 0

    fun takeActionFromPlayPauseButton() {
        when (state) {
            PodomoroState.INIT -> {
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

    private fun goToNextPomodoroType() = when (pomodoroType) {
        WORK -> {
            numberOfWorkings++
            _podomoroUiState.update { it.copy(numberOfWorking = numberOfWorkings) }
            if (numberOfWorkings % 4 == 0) {
                pomodoroType = LONG_BREAK
            } else {
                pomodoroType = BREAK
            }
        }

        BREAK -> {
            pomodoroType = WORK
        }

        LONG_BREAK -> {
            pomodoroType = WORK
        }
    }

    private fun play() {
        playWithTime(getPlayTime(pomodoroType))
    }

    private fun pause() {
        state = PodomoroState.PAUSED
        job?.cancel()
        _podomoroUiState.update { it.copy(isRunning = false) }
    }

    private fun resume() {
        playWithTime(remainTime)
    }

    private fun playWithTime(time: Long) {
        state = PodomoroState.PLAYING
        remainTime = time
        _podomoroUiState.update { it.copy(remainTime = remainTime, isRunning = true, isFinished = false) }
        job = GlobalScope.launch(Dispatchers.Main) {
            while (remainTime > 0) {
                delay(1000)
                remainTime--
                _podomoroUiState.update { it.copy(remainTime = remainTime) }
            }
            state = PodomoroState.FINISHED
            goToNextPomodoroType()
            remainTime = getPlayTime(pomodoroType)
            _podomoroUiState.update {
                it.copy(
                    remainTime = remainTime,
                    isRunning = false,
                    isFinished = true
                )
            }
        }
    }

    private fun getPlayTime(type: PomodoroType) = when (type) {
        WORK -> WORK_TIME
        BREAK -> BREAK_TIME
        LONG_BREAK -> LONG_BREAK_TIME
    }
}

data class PodomoroUiState(
    val remainTime: Long,
    val isRunning: Boolean,
    val isFinished: Boolean = false,
    val numberOfWorking: Int = 0,
)