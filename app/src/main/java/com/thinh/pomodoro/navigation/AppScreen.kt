package com.thinh.pomodoro.navigation

import androidx.navigation.NavHostController

enum class AppScreen(val route: String) {
    PODOMORO_SCREEN("podomoro_screen"),
}

class AppActions(private val navController: NavHostController) {
    fun navigateToPodomoroScreen() {
        navController.navigate(AppScreen.PODOMORO_SCREEN.route)
    }

}