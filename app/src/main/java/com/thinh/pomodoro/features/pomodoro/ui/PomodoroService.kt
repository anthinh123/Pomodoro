package com.thinh.pomodoro.features.pomodoro.ui

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.thinh.pomodoro.R
import com.thinh.pomodoro.features.pomodoro.timer.TimeState
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroManager
import com.thinh.pomodoro.utils.TimeUtil.convertMillisToTime
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

enum class PomodoroAction {
    START,
    POMODORO_ACTION,
    STOP,
    SKIP_STAGE
}

class PomodoroService(
) : Service() {

    private val POMODORO_CHANNEL_ID = 1

    private val pomodoroManager: PomodoroManager by inject()

    private lateinit var media: MediaPlayer

    private lateinit var collectPomodoroUiStateJob: Job

    private lateinit var stopPendingIntent: PendingIntent
    private lateinit var pomodoroPendingIntent: PendingIntent

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate() {
        super.onCreate()
        media = MediaPlayer.create(this, R.raw.school_bell)

        val stopIntent = Intent(this, PomodoroService::class.java)
        stopIntent.action = PomodoroAction.STOP.name
        stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val pomodoroIntent = Intent(this, PomodoroService::class.java)
        pomodoroIntent.action = PomodoroAction.POMODORO_ACTION.name
        pomodoroPendingIntent = PendingIntent.getService(
            this,
            1,
            pomodoroIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        collectPomodoroUiStateJob = GlobalScope.launch(Dispatchers.Main) {
            pomodoroManager.podomoroUiState.collect {
                val displayTime: String = convertMillisToTime(it.remainTime)
                updateNotificationTime(displayTime, it.timeState == TimeState.PLAYING)

                if (it.timeState == TimeState.FINISHED) {
                    media.start()
                    pomodoroManager.goToNextPomodoroStage()
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action?.let { PomodoroAction.valueOf(it) }
        when (action) {
            PomodoroAction.START -> {
                val notificationLayout = RemoteViews(packageName, R.layout.pomodoro_notification)
                startForeground(
                    POMODORO_CHANNEL_ID,
                    createNotification(notificationLayout = notificationLayout, isRunning = true)
                )
            }

            PomodoroAction.POMODORO_ACTION -> {
                handlePomodoroAction()
            }

            PomodoroAction.STOP -> {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }

            else -> {} // Handle invalid or null action
        }
        return START_STICKY
    }

    override fun onDestroy() {
        collectPomodoroUiStateJob.cancel()
        media.stop()
        media.release()
        super.onDestroy()
    }

    override fun onBind(p0: Intent?): IBinder {
        return Binder()
    }

    private fun handlePomodoroAction() {
        pomodoroManager.takeActionToTimer()
    }

    private fun createNotification(
        notificationLayout: RemoteViews,
        isRunning: Boolean = false,
    ): Notification {
        val notificationChannelId = "POMODORO_CHANNEL_ID"
        val notificationChannelName = "Pomodoro Timer"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                notificationChannelId,
                notificationChannelName,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
        notificationBuilder.setOngoing(true)
            .setContentTitle("Pomodoro Timer")
            .setSmallIcon(R.drawable.outline_timer_24)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .addAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                if (isRunning) "Pause" else "Play",
                pomodoroPendingIntent
            )
            .addAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                "Stop",
                stopPendingIntent
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)

        return notificationBuilder.build()
    }

    private fun updateNotificationTime(time: String, isRunning: Boolean) {
        val notificationLayout = RemoteViews(packageName, R.layout.pomodoro_notification)
        notificationLayout.setTextViewText(R.id.time, time)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = createNotification(notificationLayout, isRunning)
        notificationManager.notify(POMODORO_CHANNEL_ID, notification)
    }

}