package com.thinh.pomodoro.features.pomodoro

import com.thinh.podomoro.features.podomoro.BREAK_TIME
import com.thinh.podomoro.features.podomoro.LONG_BREAK_TIME
import com.thinh.podomoro.features.podomoro.PomodoroManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PomodoroManagerTest {

    private lateinit var pomodoroManager: PomodoroManager
    private lateinit var timer: Timer

    @Before
    fun setup() {
        timer = TimerImpl()
        pomodoroManager = PomodoroManager(timer)
    }

    @Test
    fun `playPauseButton starts timer when initial`() = runTest {
        pomodoroManager.takeActionFromPlayPauseButton()

        val uiState = pomodoroManager.podomoroUiState.first()

        assertEquals(true, uiState.isRunning)
    }

    @Test
    fun `playPauseButton pauses timer when running`() = runTest {
        pomodoroManager.takeActionFromPlayPauseButton() // to start
        pomodoroManager.takeActionFromPlayPauseButton() // to pause

        val uiState = pomodoroManager.podomoroUiState.first()

        assertEquals(false, uiState.isRunning)
    }

    @Test
    fun `playPauseButton resumes timer when paused`() = runTest {
        pomodoroManager.takeActionFromPlayPauseButton() // to start
        pomodoroManager.takeActionFromPlayPauseButton() // to pause
        pomodoroManager.takeActionFromPlayPauseButton() // to resume

        val uiState = pomodoroManager.podomoroUiState.first()

        assertEquals(true, uiState.isRunning)
    }

    @Test
    fun `playPauseButton restarts timer when finished`() = runTest {
        pomodoroManager.takeActionFromPlayPauseButton() // to start
        timer.stop() // to finish
        pomodoroManager.takeActionFromPlayPauseButton() // to restart

        val uiState = pomodoroManager.podomoroUiState.first()

        assertEquals(true, uiState.isRunning)
    }

    @Test
    fun `goes to break after work`() = runTest {
        pomodoroManager.takeActionFromPlayPauseButton() // to start work
        timer.stop() // to finish work
        pomodoroManager.takeActionFromPlayPauseButton() // to start break

        val uiState = pomodoroManager.podomoroUiState.first()

        assertEquals(BREAK_TIME, uiState.remainTime)
    }

    @Test
    fun `goes to long break after four works`() = runTest {
        for (i in 1..4) {
            pomodoroManager.takeActionFromPlayPauseButton() // to start work
            timer.stop() // to finish work
        }
        pomodoroManager.takeActionFromPlayPauseButton() // to start long break

        val uiState = pomodoroManager.podomoroUiState.first()

        assertEquals(LONG_BREAK_TIME, uiState.remainTime)
    }
}