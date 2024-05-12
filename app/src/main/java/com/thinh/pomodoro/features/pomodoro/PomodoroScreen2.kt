package com.thinh.pomodoro.features.pomodoro

import android.media.MediaPlayer
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
import androidx.compose.material3.Text
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
import com.thinh.podomoro.features.podomoro.PomodoroContract
import com.thinh.podomoro.features.podomoro.PomodoroContract.PomodoroEvent.ButtonClick
import com.thinh.pomodoro.R
import com.thinh.pomodoro.ui.theme.PomodoroTheme
import com.thinh.pomodoro.ui.theme.red_100
import com.thinh.pomodoro.ui.theme.red_50
import com.thinh.pomodoro.ui.theme.red_700
import com.thinh.pomodoro.ui.theme.red_900

@Composable
fun PomodoroScreen2(
    uiState: PomodoroContract.PomodoroUiState,
    onEvent: (PomodoroContract.PomodoroEvent) -> Unit,
) {

    val primaryColor = red_900
    val secondaryColor = red_100
    val secondaryColor2 = red_700
    val backgroundColor = red_50

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

    LaunchedEffect(uiState.playRingtone) {
        if (uiState.playRingtone) {
            media.start()
            onEvent(PomodoroContract.PomodoroEvent.PlayedRingtone)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Header(
            icon = R.drawable.ic_focus,
            title = "Focus",
            primaryColor = primaryColor,
            secondaryColor = secondaryColor
        )

        Text(
            text = "#${uiState.numberOfWorking}",
            color = primaryColor,
            fontSize = 20.sp
        )

        TimeText(time = uiState.displayTime, primaryColor = primaryColor)

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
                    .background(secondaryColor)
                    .padding(12.dp)
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = R.drawable.baseline_more_horiz_24),
                    tint = primaryColor,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add this line

            IconButton(
                onClick = { onEvent(ButtonClick) },
                modifier = Modifier
                    .weight(1.5f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(secondaryColor2)
                    .padding(12.dp)
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(
                        id = if (uiState.isRunning) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24
                    ),
                    tint = primaryColor,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add this line

            IconButton(
                onClick = { /* Handle button 3 click */ },
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(secondaryColor)
                    .padding(12.dp)
            ) {
                Icon(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource(id = R.drawable.baseline_skip_next_24),
                    tint = primaryColor,
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


@Preview(device = "spec:width=720px, height=748px, dpi=320")
@Composable
fun PodomoroScreen2Preview() {
    PomodoroTheme {
        PomodoroScreen2(
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
    PomodoroTheme {
        PomodoroScreen2(
            uiState = PomodoroContract.PomodoroUiState(
                displayTime = "25\n00",
                isRunning = false,
            ),
            onEvent = {}
        )
    }
}