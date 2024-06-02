package com.thinh.pomodoro.features.pomodoro.usecase.insert

import com.thinh.pomodoro.features.pomodoro.data.WorkDay

interface InsertWorkDayUseCase {
    suspend fun execute(workDay: WorkDay): Long
}