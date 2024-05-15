package com.thinh.pomodoro.features.pomodoro.usecase

import com.thinh.pomodoro.database.WorkDay

interface InsertWorkDayUseCase {
    suspend fun execute(workDay: WorkDay): Long
}