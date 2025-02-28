package com.thinh.pomodoro.features.pomodoro._di

import androidx.room.Room
import com.thinh.pomodoro.database.PomodoroDatabase
import com.thinh.pomodoro.database.WorkDayDao
import com.thinh.pomodoro.features.pomodoro.timer.Timer
import com.thinh.pomodoro.features.pomodoro.timer.TimerImpl
import com.thinh.pomodoro.features.pomodoro.data.WorkDayMapper
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroManager
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroManagerImpl
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroViewModel
import com.thinh.pomodoro.features.pomodoro.usecase.getnumberofworks.GetCountOfWorksInRangeUseCase
import com.thinh.pomodoro.features.pomodoro.usecase.getnumberofworks.GetCountOfWorksInRangeUseCaseImpl
import com.thinh.pomodoro.features.analytics.usecase.getworkdayinrange.GetWorkDaysInRangeUseCaseImpl
import com.thinh.pomodoro.features.analytics.usecase.getworkdayinrange.GetWorkDaysInRangeUseCase
import com.thinh.pomodoro.features.pomodoro.usecase.insert.InsertWorkDayUseCase
import com.thinh.pomodoro.features.pomodoro.usecase.insert.InsertWorkDayUseCaseImpl
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pomodoroModule = module {
    viewModel { PomodoroViewModel(get(), get()) }
    single<Timer> { TimerImpl() }
    single<WorkDayMapper> { WorkDayMapper() }
    single<InsertWorkDayUseCase> { InsertWorkDayUseCaseImpl(get(), get()) }
    single<GetCountOfWorksInRangeUseCase> { GetCountOfWorksInRangeUseCaseImpl(get()) }
    single<PomodoroManager> {
        PomodoroManagerImpl(
            defaultDispatcher = Dispatchers.Default,
            ioDispatcher = Dispatchers.IO,
            timer = get(),
            insertWorkDayUseCase = get(),
            getSettingsUseCase = get()
        )
    }
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
}
