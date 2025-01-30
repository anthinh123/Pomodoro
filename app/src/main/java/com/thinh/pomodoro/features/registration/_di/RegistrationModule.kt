package com.thinh.pomodoro.features.registration._di

import com.thinh.pomodoro.features.registration.ui.RegistrationViewModel
import com.thinh.pomodoro.features.registration.usecase.RegistrationUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registrationModule = module {
    viewModel { RegistrationViewModel(get()) }
    single <RegistrationUseCase> { RegistrationUseCase(get()) }
}