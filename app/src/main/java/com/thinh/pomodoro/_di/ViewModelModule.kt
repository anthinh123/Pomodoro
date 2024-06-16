package com.thinh.pomodoro._di

import com.thinh.pomodoro.features.pomodoro.ui.PomodoroViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PomodoroViewModel(get(), get()) }
}