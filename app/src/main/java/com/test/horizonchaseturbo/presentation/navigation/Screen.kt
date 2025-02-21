package com.test.horizonchaseturbo.presentation.navigation

sealed class Screen(val route: String) {
    object StartScreen: Screen("start_screen")
    object GameScreen: Screen("game_screen")
    object GameOverScreen: Screen("game_over_screen")
}
