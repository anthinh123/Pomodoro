package com.thinh.pomodoro.features.pomodoro.usecase.getnumberofworks

import com.thinh.pomodoro.database.WorkDayDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCountOfWorksInRangeUseCaseImpl(
    private val workDayDao: WorkDayDao,
) : GetCountOfWorksInRangeUseCase {
    override suspend fun execute(pomodoroType: Int, startDate: Long, endDate: Long): Flow<Int> {
        return workDayDao.getCountOfWorksInRange(pomodoroType, startDate, endDate).map { entities ->
            entities.size
        }
    }
}