package com.thinh.pomodoro.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDayDao {
    @Query("SELECT * FROM WorkDayEntity ORDER BY date DESC")
    fun getAllWorkDays(): Flow<List<WorkDayEntity>>

    @Insert
    suspend fun insert(workDayEntity: WorkDayEntity): Long

    @Update
    suspend fun update(workDayEntity: WorkDayEntity)

    @Query("SELECT * FROM WorkDayEntity WHERE date = :date")
    suspend fun getWorkDayByDate(date: Long): WorkDayEntity?

    @Query("SELECT * FROM WorkDayEntity WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getWorkDaysInRange(startDate: Long, endDate: Long): Flow<List<WorkDayEntity>>

    @Query("SELECT * FROM WorkDayEntity WHERE date BETWEEN :startDate AND :endDate AND type_of_pomodoro = :pomodoroType  ORDER BY date DESC")
    fun getCountOfWorksInRange(
        pomodoroType: Int,
        startDate: Long,
        endDate: Long
    ): Flow<List<WorkDayEntity>>
}