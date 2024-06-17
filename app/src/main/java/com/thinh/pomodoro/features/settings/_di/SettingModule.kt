package com.thinh.pomodoro.features.settings._di

import org.koin.androidx.viewmodel.dsl.viewModel
import com.thinh.pomodoro.features.settings.ui.SettingViewModel
import org.koin.dsl.module

val settingModule = module {
    viewModel { SettingViewModel() }
}