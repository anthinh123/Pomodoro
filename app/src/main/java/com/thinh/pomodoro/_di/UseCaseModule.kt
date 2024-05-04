package com.thinh.pomodoro._di

import com.thinh.podomoro.features.podomoro.PomodoroManager
import org.koin.dsl.module

val useCaseModule = module {
    single<PomodoroManager> { PomodoroManager() }
}