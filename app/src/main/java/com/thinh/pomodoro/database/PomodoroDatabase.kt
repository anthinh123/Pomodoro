package com.thinh.pomodoro.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WorkDayEntity::class], version = 1)
abstract class PomodoroDatabase : RoomDatabase() {
    abstract fun workDayDao(): WorkDayDao
}