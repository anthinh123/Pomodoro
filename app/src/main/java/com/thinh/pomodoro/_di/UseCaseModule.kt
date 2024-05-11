package com.thinh.pomodoro._di

import com.thinh.podomoro.features.podomoro.PomodoroManager
import com.thinh.pomodoro.features.pomodoro.Timer
import com.thinh.pomodoro.features.pomodoro.TimerImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<Timer> { TimerImpl() }
    single<PomodoroManager> { PomodoroManager(get()) }
}