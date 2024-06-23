package com.thinh.pomodoro.features.analytics.ui

import com.thinh.podomoro.mvi.BaseMviContract
import com.thinh.pomodoro.features.analytics.ui.PomodoroAnalyticsContract.PomodoroAnalyticsEvent
import com.thinh.pomodoro.features.analytics.ui.PomodoroAnalyticsContract.PomodoroAnalyticsUiState

interface PomodoroAnalyticsContract :
    BaseMviContract<PomodoroAnalyticsUiState, PomodoroAnalyticsEvent> {

    data class PomodoroAnalyticsUiState(
        val workTime: Int = 0,
        val breakTime: Int = 0,
        val longBreakTime: Int = 0,
    )

    sealed class PomodoroAnalyticsEvent {
    }
}