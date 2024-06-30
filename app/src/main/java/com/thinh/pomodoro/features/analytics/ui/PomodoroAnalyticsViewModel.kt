package com.thinh.pomodoro.features.analytics.ui

import androidx.lifecycle.viewModelScope
import com.thinh.pomodoro.features.analytics.ui.PomodoroAnalyticsContract.PomodoroAnalyticsEvent
import com.thinh.pomodoro.features.analytics.ui.PomodoroAnalyticsContract.PomodoroAnalyticsUiState
import com.thinh.pomodoro.features.analytics.usecase.getworkdayinrange.GetWorkDaysInRangeUseCase
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage
import com.thinh.pomodoro.mvi.BaseViewModel
import com.thinh.pomodoro.utils.TimeUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class PomodoroAnalyticsViewModel(
    private val getWorkDaysInRangeUseCase: GetWorkDaysInRangeUseCase,
) : BaseViewModel<PomodoroAnalyticsUiState, PomodoroAnalyticsEvent>() {


    init {
        loadWorkDays(Calendar.getInstance().time)
    }

    override fun createInitialState(): PomodoroAnalyticsUiState {
        return PomodoroAnalyticsUiState()
    }

    override fun handleEvent(event: PomodoroAnalyticsEvent) {
        when (event) {
            is PomodoroAnalyticsEvent.SelectDate -> {
                loadWorkDays(TimeUtil.convertLocalDateToDate(event.selectedDate))
            }
        }
    }

    private fun loadWorkDays(date: Date) {
        val startDate = TimeUtil.getStartTimeOfSpecificDay(date)
        val endDate = TimeUtil.getEndTimeOfSpecificDay(date)

        viewModelScope.launch(Dispatchers.IO) {
            getWorkDaysInRangeUseCase.execute(startDate, endDate).collect {
                val workingTime =
                    it.filter { workDay -> workDay.typeOfPomodoro == PomodoroStage.WORK.type }
                        .sumOf { workDay -> workDay.endTime - workDay.startTime }

                val breakTime =
                    it.filter { workDay -> workDay.typeOfPomodoro == PomodoroStage.BREAK.type }
                        .sumOf { workDay -> workDay.endTime - workDay.startTime }

                val longBreakTime =
                    it.filter { workDay -> workDay.typeOfPomodoro == PomodoroStage.LONG_BREAK.type }
                        .sumOf { workDay -> workDay.endTime - workDay.startTime }

                updateState {
                    copy(
                        workTime = convertToMinute(workingTime),
                        breakTime = convertToMinute(breakTime),
                        longBreakTime = convertToMinute(longBreakTime)
                    )
                }
            }
        }
    }

    private fun convertToMinute(time: Long): Int {
        return (time / 1000 / 60).toInt()
    }
}