package com.thinh.pomodoro.features.pomodoro.usecase.getworkdayinrange

import com.thinh.pomodoro.features.pomodoro.data.WorkDay
import kotlinx.coroutines.flow.Flow

interface GetWorkDaysInRangeUseCase {
    suspend fun execute(startDate: Long, endDate: Long): Flow<List<WorkDay>>
}