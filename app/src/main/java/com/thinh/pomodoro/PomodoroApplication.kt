package com.thinh.pomodoro

import android.app.Application
import com.thinh.pomodoro._di.useCaseModule
import com.thinh.pomodoro._di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PomodoroApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = viewModelModule + useCaseModule
        startKoin {
            androidContext(this@PomodoroApplication)
            modules(modules)
        }
    }
}