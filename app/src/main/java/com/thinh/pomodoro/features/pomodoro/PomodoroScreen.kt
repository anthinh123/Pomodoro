package com.thinh.pomodoro.features.pomodoro

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LifecycleEventEffect
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

    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
        Log.d("thinhav", "PodomoroScreen LifecycleEventEffect ON_CREATE")
    }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        Log.d("thinhav", "PodomoroScreen LifecycleEventEffect ON_RESUME")
    }

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        Log.d("thinhav", "PodomoroScreen LifecycleEventEffect ON_STOP")
    }


    var pomodoroService by remember { mutableStateOf<PomodoroService?>(null) }
    var timeUpdate by remember { mutableStateOf("") }

    var isBound = remember { false }

//    val serviceConnection = remember {
//        object : ServiceConnection {
//            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
//                val binder = service as PomodoroService.LocalBinder
//                pomodoroService = binder.getService()
//                isBound = true
//                println("thinhav onServiceConnected: $pomodoroService")
//            }
//
//            override fun onServiceDisconnected(name: ComponentName?) {
//                isBound = false
//            }
//        }
//    }

//    LaunchedEffect(pomodoroService) {
//        println("thinhav LaunchedEffect(pomodoroService" + pomodoroService)
//        if (pomodoroService != null) {
//            pomodoroService?.timeUpdateFlow?.collect {
//                timeUpdate = it
//                println("timeUpdate: $timeUpdate")
//            }
//        }
//
//    }

//    LaunchedEffect(key1 = Unit) {
//        val intent = Intent(context, PomodoroService::class.java)
//        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
//    }


    DisposableEffect(Unit) {
        onDispose {
//            if (isBound) {
//                println("thinhav DisposableEffect isBound service : $pomodoroService")
//                context.unbindService(serviceConnection)
//                isBound = false
//            }
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
            onClick = {
//                sendActionToService(context)
                onEvent(PomodoroEvent.ButtonClick)
            }
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

fun sendActionToService(context: Context) {
    val intent = Intent(context, PomodoroService::class.java)
    intent.action = PomodoroAction.POMODORO_ACTION.name
    context.startService(intent)
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