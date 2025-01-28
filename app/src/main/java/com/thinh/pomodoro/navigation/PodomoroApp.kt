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
import com.thinh.pomodoro.features.analytics.ui.PomodoroAnalyticsScreen
import com.thinh.pomodoro.features.analytics.ui.PomodoroAnalyticsViewModel
import com.thinh.pomodoro.features.login.ui.LoginScreen
import com.thinh.pomodoro.features.login.ui.LoginViewModel
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroViewModel
import com.thinh.pomodoro.features.pomodoro.ui.PomodoroScreen
import com.thinh.pomodoro.features.registration.ui.RegistrationScreen
import com.thinh.pomodoro.features.registration.ui.RegistrationViewModel
import com.thinh.pomodoro.features.settings.ui.SettingScreen
import com.thinh.pomodoro.features.settings.ui.SettingViewModel
import com.thinh.pomodoro.navigation.AppScreen.POMODORO_ANALYTICS_SCREEN
import com.thinh.pomodoro.navigation.AppScreen.SETTING_SCREEN
import com.thinh.pomodoro.ui.theme.PomodoroColorScheme
import com.thinh.pomodoro.ui.theme.PomodoroTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun PodomoroApp() {
    var colorScheme by remember { mutableStateOf(PomodoroColorScheme.WORKING_COLOR) }
    val isSystemDarkMode = isSystemInDarkTheme()
    var darkMode by remember { mutableStateOf(isSystemDarkMode) }

    val navController = rememberNavController()

    PomodoroTheme(
        pomodoroColorScheme = colorScheme,
        darkMode = darkMode
    ) {
        Scaffold {
            PodomoroNavGraph(
                updateColorScheme = { color -> colorScheme = color },
                updateDarkMode = { isDarkMode -> darkMode = isDarkMode },
                navController = navController,
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
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
//        startDestination = AppScreen.PODOMORO_SCREEN.route,
        startDestination = AppScreen.LOGIN_SCREEN.route,
        modifier = modifier,
    ) {

        composable(AppScreen.LOGIN_SCREEN.route) {
            val viewModel: LoginViewModel = koinViewModel()
            LoginScreen(
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                onEvent = { viewModel.handleEvent(it) },
                navigateToRegisterScreen = { navController.navigate(AppScreen.REGISTER_SCREEN.route) }
            )
        }

        composable(AppScreen.REGISTER_SCREEN.route) {
            val viewModel: RegistrationViewModel = koinViewModel()
            RegistrationScreen(
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                onEvent = { viewModel.handleEvent(it) },
                onBack = { navController.popBackStack() },
            )
        }

        composable(AppScreen.PODOMORO_SCREEN.route) {
            val viewModel: PomodoroViewModel = koinViewModel()
            PomodoroScreen(
                updateColorScheme = { updateColorScheme(it) },
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                onEvent = { viewModel.handleEvent(it) },
                navigateToSettingScreen = { navController.navigate(SETTING_SCREEN.route) },
                navigateToAnalyticsScreen = { navController.navigate(POMODORO_ANALYTICS_SCREEN.route) }
            )
        }

        composable(SETTING_SCREEN.route) {
            val viewModel: SettingViewModel = koinViewModel()
            SettingScreen(
                updateDarkMode = { updateDarkMode(it) },
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                onEvent = { viewModel.handleEvent(it) },
                onBack = { navController.popBackStack() },
            )
        }

        composable(POMODORO_ANALYTICS_SCREEN.route) {
            val viewModel: PomodoroAnalyticsViewModel = koinViewModel()
            PomodoroAnalyticsScreen(
                uiState = viewModel.uiState.collectAsStateWithLifecycle().value,
                onEvent = { viewModel.handleEvent(it) },
                isDarkMode = isSystemInDarkTheme(),
                onBack = { navController.popBackStack() }
            )

        }
    }
}
