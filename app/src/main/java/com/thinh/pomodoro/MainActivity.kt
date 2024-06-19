package com.thinh.pomodoro

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.thinh.pomodoro.navigation.PodomoroApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PodomoroApp()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!checkNotificationPermission()) {
            showNotificationPermissionDialog()
        }
    }

    private fun checkNotificationPermission(): Boolean {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return notificationManager.areNotificationsEnabled()
    }

    private fun showNotificationPermissionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Notifications Disabled")
        builder.setMessage("Please enable notifications for our app in your settings.")
        builder.setPositiveButton("Open Settings") { _, _ ->
            // Open the app's system settings when the user taps 'Open Settings'
            val intent = Intent()
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)
            startActivity(intent)
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
    }
}