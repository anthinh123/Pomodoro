package com.thinh.pomodoro.features.pomodoro.usecase.getworkdayinrange

import com.thinh.pomodoro.database.WorkDayDao
import com.thinh.pomodoro.features.pomodoro.data.WorkDay
import com.thinh.pomodoro.features.pomodoro.data.WorkDayMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetWorkDaysInRangeUseCaseImpl(
    private val workDayDao: WorkDayDao,
    private val workDayMapper: WorkDayMapper,
) : GetWorkDaysInRangeUseCase {
    override suspend fun execute(startDate: Long, endDate: Long): Flow<List<WorkDay>> {
        return workDayDao.getWorkDaysInRange(startDate, endDate).map { entities ->
            entities.map { workDayMapper.mapFromEntity(it) } ?: emptyList()
        }
    }
}