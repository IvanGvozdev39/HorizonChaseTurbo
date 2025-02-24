package com.test.horizonchaseturbo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.test.horizonchaseturbo.R
import com.test.horizonchaseturbo.presentation.navigation.Screen
import com.test.horizonchaseturbo.ui.theme.Black0
import kotlinx.coroutines.delay

@Composable
fun GameOverScreen(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Black0)) {
        Image(painter = painterResource(id = R.drawable.gameover), contentDescription = stringResource(
            id = R.string.game_over
        ),
            modifier = Modifier.align(Alignment.Center)
                .fillMaxWidth())
    }

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(Screen.StartScreen.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}