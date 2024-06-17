package com.thinh.pomodoro.navigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroViewModel
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroScreen
import com.thinh.pomodoro.features.settings.ui.SettingScreen
import com.thinh.pomodoro.features.settings.ui.SettingViewModel
import com.thinh.pomodoro.ui.theme.PomodoroColorScheme
import com.thinh.pomodoro.ui.theme.PomodoroTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PodomoroApp() {
    var colorScheme by remember { mutableStateOf(PomodoroColorScheme.WORKING_COLOR) }
    val isSystemDarkMode = isSystemInDarkTheme()
    var darkMode by remember { mutableStateOf(isSystemDarkMode) }

    val navController = rememberNavController()
    val navActions: AppActions = remember(navController) {
        AppActions(navController)
    }

    PomodoroTheme(
        pomodoroColorScheme = colorScheme,
        darkMode = darkMode
    ) {
        Scaffold {
            PodomoroNavGraph(
                updateColorScheme = { color -> colorScheme = color },
                updateDarkMode = { isDarkMode -> darkMode = isDarkMode },
                navController = navController,
                navActions = navActions,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
            )
        }
    }
}

@Composable
fun PodomoroNavGraph(
    updateColorScheme: (PomodoroColorScheme) -> Unit,
    updateDarkMode: (Boolean) -> Unit,
    navController: NavHostController,
    navActions: AppActions,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.PODOMORO_SCREEN.route,
        modifier = modifier,
    ) {
        composable(AppScreen.PODOMORO_SCREEN.route) {
            val viewModel: PomodoroViewModel = koinViewModel()
            PomodoroScreen(
                updateColorScheme = { updateColorScheme(it) },
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                onEvent = { viewModel.handleEvent(it) },
                navigateToSettingScreen = { navController.navigate(AppScreen.SETTING_SCREEN.route) },
            )
        }

        composable(AppScreen.SETTING_SCREEN.route) {
            val viewModel: SettingViewModel = koinViewModel()
            SettingScreen(
                updateDarkMode = { updateDarkMode(it) },
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                onEvent = { viewModel.handleEvent(it) },
                onBack = { navController.popBackStack() },
            )
        }
    }
}
