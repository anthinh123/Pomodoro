package com.thinh.pomodoro.features.pomodoro.usecase

import com.thinh.pomodoro.database.WorkDay
import com.thinh.pomodoro.database.WorkDayDao

class InsertWorkDayUseCaseImpl(private val workDayDao: WorkDayDao) : InsertWorkDayUseCase {
    override suspend fun execute(workDay: WorkDay): Long {
        return workDayDao.insert(workDay)
    }
}