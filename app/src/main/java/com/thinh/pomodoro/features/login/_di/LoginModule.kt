package com.thinh.pomodoro.features.login._di

import com.thinh.pomodoro.features.auth.SessionManager
import com.thinh.pomodoro.features.login.ui.LoginViewModel
import com.thinh.pomodoro.features.login.usecase.LoginUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get(), get()) }
    single<LoginUseCase> { LoginUseCase(get()) }
    single<SessionManager> {SessionManager(androidContext())}
}