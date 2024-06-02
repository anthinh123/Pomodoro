package com.thinh.pomodoro

import android.app.Application
import com.thinh.pomodoro._di.viewModelModule
import com.thinh.pomodoro.features.pomodoro._di.pomodoroModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PomodoroApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = viewModelModule + pomodoroModule
        startKoin {
            androidContext(this@PomodoroApplication)
            modules(modules)
        }
    }
}