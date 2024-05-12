package com.thinh.pomodoro.features.pomodoro

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TimerImplTest {

    private lateinit var timerImpl: TimerImpl

    @Before
    fun setup() {
        timerImpl = TimerImpl()
    }

    @Test
    fun `play sets timerState to running and not finished`() = runTest {
        timerImpl.play(10)

        val timerState = timerImpl.timerState.first()

        assertEquals(true, timerState.isRunning)
        assertEquals(false, timerState.isFinished)
    }

    @Test
    fun `pause sets timerState to not running and not finished`() = runTest {
        timerImpl.play(10)
        timerImpl.pause()

        val timerState = timerImpl.timerState.first()

        assertEquals(false, timerState.isRunning)
        assertEquals(false, timerState.isFinished)
    }

    @Test
    fun `stop sets timerState to not running and finished`() = runTest {
        timerImpl.play(10)
        timerImpl.stop()

        val timerState = timerImpl.timerState.first()

        assertEquals(false, timerState.isRunning)
        assertEquals(true, timerState.isFinished)
    }
}