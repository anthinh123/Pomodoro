package com.thinh.pomodoro

import android.app.Application
import com.thinh.pomodoro.features.analytics._di.pomodoroAnalyticsModule
import com.thinh.pomodoro.features.pomodoro._di.pomodoroModule
import com.thinh.pomodoro.features.settings._di.settingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PomodoroApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = pomodoroModule + settingModule + pomodoroAnalyticsModule
        startKoin {
            androidContext(this@PomodoroApplication)
            modules(modules)
        }
    }
}