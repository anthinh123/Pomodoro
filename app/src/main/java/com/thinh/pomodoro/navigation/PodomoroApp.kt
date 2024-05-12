package com.thinh.pomodoro.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thinh.podomoro.features.podomoro.PodomoroViewModel
import com.thinh.pomodoro.features.pomodoro.PodomoroScreen
import com.thinh.pomodoro.features.pomodoro.PomodoroScreen2
import org.koin.androidx.compose.koinViewModel

@Composable
fun PodomoroApp() {
    val navController = rememberNavController()
    val navActions: AppActions = remember(navController) {
        AppActions(navController)
    }

    Scaffold {
        PodomoroNavGraph(
            navController = navController,
            navActions = navActions,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
    }
}

@Composable
fun PodomoroNavGraph(
    navController: NavHostController,
    navActions: AppActions,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.PODOMORO_SCREEN.route,
        modifier = modifier,
    ) {
        composable(AppScreen.PODOMORO_SCREEN.route) {
            val viewModel : PodomoroViewModel = koinViewModel()
            PomodoroScreen2(
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                onEvent = { viewModel.handleEvent(it) }
            )
        }
    }
}
