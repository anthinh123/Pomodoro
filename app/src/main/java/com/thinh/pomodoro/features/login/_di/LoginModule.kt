package com.thinh.pomodoro.features.login._di

import com.thinh.pomodoro.features.login.network.AuthenticationApi
import com.thinh.pomodoro.features.login.ui.LoginViewModel
import com.thinh.pomodoro.features.login.usecase.LoginUseCase
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val loginModule = module {
    viewModel { LoginViewModel(get()) }
    single<LoginUseCase> { LoginUseCase(get()) }
}