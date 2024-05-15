package com.thinh.pomodoro.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.thinh.pomodoro.database.WorkDay
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkDayDao {
    @Query("SELECT * FROM WorkDay ORDER BY date DESC")
    fun getAllWorkDays(): Flow<List<WorkDay>>

    @Insert
    suspend fun insert(workDay: WorkDay): Long

    @Update
    suspend fun update(workDay: WorkDay)

    @Query("SELECT * FROM WorkDay WHERE date = :date")
    suspend fun getWorkDayByDate(date: Long): WorkDay?
}