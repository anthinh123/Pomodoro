package com.thinh.pomodoro.features.pomodoro.usecase.insert

import com.thinh.pomodoro.database.WorkDayDao
import com.thinh.pomodoro.features.pomodoro.data.WorkDay
import com.thinh.pomodoro.features.pomodoro.data.WorkDayMapper

class InsertWorkDayUseCaseImpl(
    private val workDayDao: WorkDayDao,
    private val workDayMapper: WorkDayMapper,
) : InsertWorkDayUseCase {
    override suspend fun execute(workDay: WorkDay): Long {
        return workDayDao.insert(workDayMapper.mapToEntity(workDay))
    }
}