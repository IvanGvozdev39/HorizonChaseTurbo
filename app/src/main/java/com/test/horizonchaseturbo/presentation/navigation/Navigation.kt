package com.test.horizonchaseturbo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.test.horizonchaseturbo.presentation.screens.GameScreen
import com.test.horizonchaseturbo.presentation.screens.StartScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.StartScreen.route) {
        composable(route = Screen.StartScreen.route) {
            StartScreen(navController = navController)
        }
        composable(route = Screen.GameScreen.route) {
            GameScreen(navController = navController)
        }
    }
}
