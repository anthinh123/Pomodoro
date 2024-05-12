package com.thinh.pomodoro.features.pomodoro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thinh.podomoro.features.podomoro.PomodoroContract
import com.thinh.podomoro.features.podomoro.PomodoroContract.PomodoroEvent.ButtonClick
import com.thinh.podomoro.features.podomoro.PomodoroStage
import com.thinh.pomodoro.R
import com.thinh.pomodoro.ui.theme.PomodoroColorScheme
import com.thinh.pomodoro.ui.theme.PomodoroTheme
import com.thinh.pomodoro.ui.theme.short_break_dark_background
import com.thinh.pomodoro.ui.theme.short_break_dark_primary
import com.thinh.pomodoro.ui.theme.short_break_dark_secondary
import com.thinh.pomodoro.ui.theme.short_break_dark_tertiary
import com.thinh.pomodoro.ui.theme.short_break_light_background
import com.thinh.pomodoro.ui.theme.short_break_light_primary
import com.thinh.pomodoro.ui.theme.short_break_light_secondary
import com.thinh.pomodoro.ui.theme.short_break_light_tertiary
import com.thinh.pomodoro.ui.theme.working_light_secondary
import com.thinh.pomodoro.ui.theme.working_light_background
import com.thinh.pomodoro.ui.theme.working_light_tertiary
import com.thinh.pomodoro.ui.theme.working_light_primary

@Composable
fun PomodoroScreen2(
    uiState: PomodoroContract.PomodoroUiState,
    onEvent: (PomodoroContract.PomodoroEvent) -> Unit,
    updateColorScheme: (PomodoroColorScheme) -> Unit
) {

//    val context = LocalContext.current
//    val media: MediaPlayer = remember {
//        MediaPlayer.create(context, R.raw.school_bell)
//    }
//
//    DisposableEffect(Unit) {
//        onDispose {
//            media.stop()
//            media.release()
//        }
//    }
//
//    LaunchedEffect(uiState.playRingtone) {
//        if (uiState.playRingtone) {
//            media.start()
//            onEvent(PomodoroContract.PomodoroEvent.PlayedRingtone)
//        }
//    }

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
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header(
            icon = getHeaderIcon(uiState.pomodoroStage),
            title = getHeaderText(uiState.pomodoroStage),
            primaryColor = MaterialTheme.colorScheme.primary,
            secondaryColor = MaterialTheme.colorScheme.secondary
        )

        Text(
            text = "#${uiState.numberOfWorking}",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        )

        TimeText(time = uiState.displayTime, primaryColor = MaterialTheme.colorScheme.primary)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = { /* Handle button 1 click */ },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(12.dp)
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = R.drawable.rounded_more_horiz_24),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add this line

            IconButton(
                onClick = { onEvent(ButtonClick) },
                modifier = Modifier
                    .weight(1.5f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.tertiary)
                    .padding(12.dp)
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(
                        id = if (uiState.isRunning) R.drawable.round_pause_24 else R.drawable.round_play_arrow_24
                    ),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add this line

            IconButton(
                onClick = { /* Handle button 3 click */ },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(12.dp)
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

@Composable
private fun TimeText(time: String, primaryColor: Color) {
    Text(
        text = time,
        fontSize = 200.sp,
        fontWeight = Bold,
        textAlign = Center,
        color = primaryColor,
        lineHeight = 160.sp,
        letterSpacing = 0.sp,
        style = TextStyle(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            ),
        )
    )
}

@Composable
private fun Header(
    icon: Int,
    title: String,
    primaryColor: Color,
    secondaryColor: Color
) {
    Card(
        shape = RoundedCornerShape(20.dp),
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
        PomodoroStage.WORK -> "Focus"
        PomodoroStage.BREAK -> "Short Break"
        PomodoroStage.LONG_BREAK -> "Long Break"
    }
}

private fun getHeaderIcon(pomodoroStage: PomodoroStage): Int {
    return when (pomodoroStage) {
        PomodoroStage.WORK -> R.drawable.rounded_local_fire_department_24
        PomodoroStage.BREAK -> R.drawable.ic_coffee
        PomodoroStage.LONG_BREAK -> R.drawable.ic_coffee
    }
}

@Preview(device = "spec:width=720px, height=748px, dpi=320")
@Composable
fun PodomoroScreen2Preview() {
    PomodoroTheme(
        pomodoroColorScheme = PomodoroColorScheme.WORKING_COLOR
    ) {
        PomodoroScreen2(
            updateColorScheme = {},
            uiState = PomodoroContract.PomodoroUiState(
                displayTime = "25 : 00",
                isRunning = false,
            ),
            onEvent = {}
        )
    }
}

@Preview(device = "spec:width=2640px,height=1080px,dpi=480,orientation=portrait")
@Composable
fun PodomoroScreen2Preview2() {
    PomodoroTheme(
        pomodoroColorScheme = PomodoroColorScheme.SHORT_BREAK_COLOR
    ) {
        PomodoroScreen2(
            updateColorScheme = {},
            uiState = PomodoroContract.PomodoroUiState(
                displayTime = "25\n00",
                isRunning = false,
            ),
            onEvent = {}
        )
    }
}