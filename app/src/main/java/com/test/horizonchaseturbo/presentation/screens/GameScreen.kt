package com.test.horizonchaseturbo.presentation.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.test.horizonchaseturbo.R
import com.test.horizonchaseturbo.presentation.util.RoadMarkings
import com.test.horizonchaseturbo.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel = hiltViewModel()) {
    val playerLane by viewModel::playerLane
    val policeCars = viewModel.policeCars
    val density = LocalDensity.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
    val laneWidthPx = with(density) { (screenWidthDp / 3).toPx() }

    val lanePositions = listOf(
        laneWidthPx * 0.75f,
        laneWidthPx * 1.5f,
        laneWidthPx * 2.25f
    )

    val lanePositionsPlayer = listOf(
        -laneWidthPx * 0.75f,
        0f,
        laneWidthPx * 0.75f
    )

    val transition = updateTransition(targetState = playerLane, label = "laneSwitch")
    val carOffsetX by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 50, easing = LinearEasing) }, label = ""
    ) { lane ->
        lanePositionsPlayer[lane - 1]
    }

    LaunchedEffect(Unit) {
        viewModel.spawnPoliceCars(density).collect { newPoliceCar ->
            viewModel.addPoliceCar(newPoliceCar, density)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        RoadMarkings(
            lineCount = 4,
            markingHeight = 101.dp,
            markingSpacing = 51.dp,
            speed = 3f,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 150.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.player_car),
                contentDescription = "Player Car",
                modifier = Modifier
                    .height(103.dp)
                    .graphicsLayer(
                        translationX = carOffsetX
                    )
            )
        }

        val policeCarImage = ImageBitmap.imageResource(id = R.drawable.police)
        val policeCarHeight = with(density) { 119.dp.toPx() }
        val policeCarAspectRatio = policeCarImage.width.toFloat() / policeCarImage.height.toFloat()
        val policeCarWidth = policeCarHeight * policeCarAspectRatio

        Canvas(modifier = Modifier.fillMaxSize()) {
            viewModel.updatePoliceCarPositions(size.height)
            policeCars.forEach { policeCar ->
                val policeCarOffsetX = lanePositions[policeCar.lane - 1] - policeCarWidth / 2
                val policeCarOffsetY = policeCar.offsetY
                withTransform({
                    scale(
                        scaleX = policeCarWidth / policeCarImage.width,
                        scaleY = policeCarHeight / policeCarImage.height,
                        pivot = Offset(policeCarOffsetX, policeCarOffsetY)
                    )
                }) {
                    drawImage(
                        image = policeCarImage,
                        topLeft = Offset(policeCarOffsetX, policeCarOffsetY)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(vertical = 48.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "Move Left",
                modifier = Modifier
                    .size(90.dp)
                    .clickable {
                        if (playerLane > 1) viewModel.movePlayerCarLeft()
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Move Right",
                modifier = Modifier
                    .size(90.dp)
                    .clickable {
                        if (playerLane < 3) viewModel.movePlayerCarRight()
                    }
            )
        }
    }
}
