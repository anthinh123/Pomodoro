package com.thinh.pomodoro

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.thinh.pomodoro.features.pomodoro.PomodoroAction
import com.thinh.pomodoro.features.pomodoro.PomodoroService
import com.thinh.pomodoro.navigation.PodomoroApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val intent = Intent(this, PomodoroService::class.java)
        intent.action = PomodoroAction.START.name
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(intent)
//        } else {
//            startService(intent)
//        }
        setContent {
            PodomoroApp()
        }
    }
}