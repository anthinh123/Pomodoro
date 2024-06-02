package com.thinh.pomodoro.features.pomodoro.data

import com.thinh.pomodoro.database.WorkDayEntity

class WorkDayMapper {
    fun mapToEntity(workDay: WorkDay): WorkDayEntity {
        return WorkDayEntity(
            id = workDay.id,
            date = workDay.date,
            typeOfPomodoro = workDay.typeOfPomodoro,
            startTime = workDay.startTime,
            endTime = workDay.endTime
        )
    }

    fun mapFromEntity(workDayEntity: WorkDayEntity): WorkDay {
        return WorkDay(
            id = workDayEntity.id,
            date = workDayEntity.date,
            typeOfPomodoro = workDayEntity.typeOfPomodoro,
            startTime = workDayEntity.startTime,
            endTime = workDayEntity.endTime
        )
    }
}