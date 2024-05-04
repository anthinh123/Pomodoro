package com.thinh.pomodoro.features.pomodoro

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thinh.podomoro.features.podomoro.PomodoroContract.PomodoroEvent
import com.thinh.podomoro.features.podomoro.PomodoroContract.PomodoroUiState
import com.thinh.pomodoro.R
import com.thinh.pomodoro.ui.theme.PomodoroTheme


@Composable
fun PodomoroScreen(
    uiState: PomodoroUiState,
    onEvent: (PomodoroEvent) -> Unit,
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

    LaunchedEffect(uiState.playRingtone) {
        if (uiState.playRingtone) {
            media.start()
            onEvent(PomodoroEvent.PlayedRingtone)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .weight(0.5f)
                .wrapContentHeight(),

            ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.img_work_5),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )

                Text(
                    text = "#${uiState.numberOfWorking}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Text(
            modifier = Modifier.weight(0.3f),
            text = uiState.displayTime,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 80.sp,
                fontFamily = FontFamily.Serif
            )
        )

        FilledTonalButton(
            modifier = Modifier.weight(0.2f),
            shape = RoundedCornerShape(32),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.Black,
            ),
            onClick = { onEvent(PomodoroEvent.ButtonClick) }
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 48.dp),
                text = if (uiState.isRunning) "Pause" else "Start",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
        }
    }
}

@Preview(device = "spec:width=720px, height=748px, dpi=320")
@Composable
fun PodomoroScreenPreview() {
    PomodoroTheme {
        PodomoroScreen(
            uiState = PomodoroUiState(
                displayTime = "25 : 00",
                isRunning = false,
            ),
            onEvent = {}
        )
    }
}

@Preview(device = "spec:width=2640px,height=1080px,dpi=480,orientation=portrait")
@Composable
fun PodomoroScreenPreview2() {
    PomodoroTheme {
        PodomoroScreen(
            uiState = PomodoroUiState(
                displayTime = "25 : 00",
                isRunning = false,
            ),
            onEvent = {}
        )
    }
}