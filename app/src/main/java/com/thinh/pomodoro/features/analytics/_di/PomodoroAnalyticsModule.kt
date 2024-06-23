package com.thinh.pomodoro.features.analytics._di

import com.thinh.pomodoro.features.analytics.ui.PomodoroAnalyticsViewModel
import com.thinh.pomodoro.features.analytics.usecase.getworkdayinrange.GetWorkDaysInRangeUseCase
import com.thinh.pomodoro.features.analytics.usecase.getworkdayinrange.GetWorkDaysInRangeUseCaseImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pomodoroAnalyticsModule = module {
    viewModel { PomodoroAnalyticsViewModel(get()) }
    single<GetWorkDaysInRangeUseCase> { GetWorkDaysInRangeUseCaseImpl(get(), get()) }
}