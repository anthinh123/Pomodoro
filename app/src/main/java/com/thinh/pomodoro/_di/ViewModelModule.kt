package com.thinh.pomodoro._di

import com.thinh.podomoro.features.podomoro.PodomoroViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PodomoroViewModel(get()) }
}