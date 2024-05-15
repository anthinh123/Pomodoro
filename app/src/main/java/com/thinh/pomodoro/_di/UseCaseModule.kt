package com.thinh.pomodoro._di

import androidx.room.Room
import com.thinh.podomoro.features.pomodoro.PomodoroManager
import com.thinh.pomodoro.database.PomodoroDatabase
import com.thinh.pomodoro.database.WorkDayDao
import com.thinh.pomodoro.features.pomodoro.Timer
import com.thinh.pomodoro.features.pomodoro.TimerImpl
import com.thinh.pomodoro.features.pomodoro.usecase.InsertWorkDayUseCase
import com.thinh.pomodoro.features.pomodoro.usecase.InsertWorkDayUseCaseImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val useCaseModule = module {
    single<Timer> { TimerImpl() }
    single<PomodoroManager> { PomodoroManager(get()) }
    single<PomodoroDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            PomodoroDatabase::class.java,
            "pomodoro.db"
        ).fallbackToDestructiveMigration().build()
    }

    single<WorkDayDao> {
        get<PomodoroDatabase>().workDayDao()
    }
    single<InsertWorkDayUseCase> { InsertWorkDayUseCaseImpl(get()) }

}