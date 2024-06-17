package com.thinh.pomodoro.features.pomodoro.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.room.util.TableInfo
import com.thinh.pomodoro.R
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroEvent.PlayPauseEvent
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroEvent.ResetTime
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroContract.PomodoroEvent.SkipStage
import com.thinh.pomodoro.features.pomodoro.timer.TimeState
import com.thinh.pomodoro.features.pomodoro.pomodoromanager.PomodoroStage
import com.thinh.pomodoro.ui.theme.PomodoroColorScheme
import com.thinh.pomodoro.ui.theme.PomodoroTheme
import com.thinh.pomodoro.utils.AutoSizeText

@Composable
fun PomodoroScreen(
    uiState: PomodoroContract.PomodoroUiState,
    onEvent: (PomodoroContract.PomodoroEvent) -> Unit,
    updateColorScheme: (PomodoroColorScheme) -> Unit,
    navigateToSettingScreen: () -> Unit,
) {
    val context = LocalContext.current
    val media: MediaPlayer = remember {
        MediaPlayer.create(context, R.raw.school_bell)
    }

    DisposableEffect(Unit) {
        onDispose {
            media.stop()
            media.release()
        }
    }

    LaunchedEffect(uiState.timeState) {
        if (uiState.timeState == TimeState.FINISHED) {
            media.start()
            onEvent(PomodoroContract.PomodoroEvent.PlayedRingtone)
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        stopPomodoroService(context)
    }

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        if (uiState.timeState == TimeState.PAUSED || uiState.timeState == TimeState.PLAYING) {
            startPomodoroService(context)
        }
    }

    LaunchedEffect(uiState.pomodoroStage) {
        when (uiState.pomodoroStage) {
            PomodoroStage.WORK -> updateColorScheme(PomodoroColorScheme.WORKING_COLOR)
            PomodoroStage.BREAK -> updateColorScheme(PomodoroColorScheme.SHORT_BREAK_COLOR)
            PomodoroStage.LONG_BREAK -> updateColorScheme(PomodoroColorScheme.LONG_BREAK_COLOR)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(
                modifier = Modifier.padding(end = 16.dp),
                onClick = { navigateToSettingScreen() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Settings,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Header(
                    icon = getHeaderIcon(uiState.pomodoroStage),
                    title = getHeaderText(uiState.pomodoroStage),
                    primaryColor = MaterialTheme.colorScheme.primary,
                    secondaryColor = MaterialTheme.colorScheme.secondary
                )

                AutoSizeText(
                    text = "#${uiState.numberOfWorking}",
                    color = MaterialTheme.colorScheme.primary,
                    maxFontSize = 20.sp
                )
            }
        }

        AutoSizeText(
            modifier = Modifier
                .fillMaxSize()
                .weight(4f),
            text = uiState.displayTime,
            fontWeight = Bold,
            textAlign = Center,
            color = MaterialTheme.colorScheme.primary,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = true
                ),
            )
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onEvent(ResetTime) },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = R.drawable.round_replay_24),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = { onEvent(PlayPauseEvent) },
                modifier = Modifier
                    .weight(1.5f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(16.dp)
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(
                        id = if (uiState.timeState == TimeState.PLAYING) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24
                    ),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            IconButton(
                onClick = { onEvent(SkipStage) },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = R.drawable.round_skip_next_24),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }
        }
    }
}

fun startPomodoroService(context: Context) {
    val intent = Intent(context, PomodoroService::class.java)
    intent.action = PomodoroAction.START.name
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
    } else {
        context.startService(intent)
    }
}

fun stopPomodoroService(context: Context) {
    if (checkServiceRunning(context, PomodoroService::class.java)) {
        val intent = Intent(context, PomodoroService::class.java)
        intent.action = PomodoroAction.STOP.name
        context.startService(intent)
    }
}

fun checkServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
    val manager = context.getSystemService(ActivityManager::class.java)
    for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}


@Composable
private fun Header(
    icon: Int,
    title: String,
    primaryColor: Color,
    secondaryColor: Color,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, primaryColor),
        colors = CardDefaults.cardColors(containerColor = secondaryColor),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Icon(
                painter = painterResource(id = icon),
                tint = primaryColor,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, color = primaryColor)
        }
    }
}

private fun getHeaderText(pomodoroStage: PomodoroStage): String {
    return when (pomodoroStage) {
        PomodoroStage.WORK -> "Work"
        PomodoroStage.BREAK -> "Short Break"
        PomodoroStage.LONG_BREAK -> "Long Break"
    }
}

private fun getHeaderIcon(pomodoroStage: PomodoroStage): Int {
    return when (pomodoroStage) {
        PomodoroStage.WORK -> R.drawable.round_laptop_24
        PomodoroStage.BREAK -> R.drawable.ic_coffee
        PomodoroStage.LONG_BREAK -> R.drawable.round_chair_24
    }
}

@Preview(device = "spec:width=720px, height=748px, dpi=320")
@Composable
fun PodomoroScreen2Preview() {
    PomodoroTheme(
        pomodoroColorScheme = PomodoroColorScheme.WORKING_COLOR,
        darkMode = false
    ) {
        PomodoroScreen(
            updateColorScheme = {},
            uiState = PomodoroContract.PomodoroUiState(
                displayTime = "25\n" +
                        "00",
                timeState = TimeState.INIT,
            ),
            onEvent = {},
            navigateToSettingScreen = {}
        )
    }
}

@Preview(device = "spec:width=2640px,height=1080px,dpi=480,orientation=portrait")
@Composable
fun PodomoroScreen2Preview2() {
    PomodoroTheme(
        pomodoroColorScheme = PomodoroColorScheme.SHORT_BREAK_COLOR,
        darkMode = true
    ) {
        PomodoroScreen(
            updateColorScheme = {},
            uiState = PomodoroContract.PomodoroUiState(
                displayTime = "25\n00",
                timeState = TimeState.INIT,
            ),
            onEvent = {},
            navigateToSettingScreen = {}
        )
    }
}