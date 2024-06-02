package com.thinh.pomodoro.features.pomodoro.usecase.getnumberofworks

import kotlinx.coroutines.flow.Flow

interface GetCountOfWorksInRangeUseCase {
    suspend fun execute(pomodoroType: Int, startDate: Long, endDate: Long): Flow<Int>
}