package com.test.horizonchaseturbo.presentation.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.test.horizonchaseturbo.R

@Composable
fun GameScreen(navController: NavController) {
    var playerLane by remember { mutableIntStateOf(2) }
    val transition = updateTransition(targetState = playerLane, label = "laneSwitch")

    val laneWidth = LocalConfiguration.current.screenWidthDp.dp / 5
    val carOffsetX by transition.animateFloat(
        transitionSpec = {
            tween(durationMillis = 100, easing = LinearEasing)
        }, label = ""
    ) { lane ->
        (lane - 2) * laneWidth.value * 3.4f
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
                        if (playerLane > 1) playerLane--
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.arrow_right),
                contentDescription = "Move Right",
                modifier = Modifier
                    .size(90.dp)
                    .clickable {
                        if (playerLane < 3) playerLane++
                    }
            )
        }
    }
}

@Composable
fun RoadMarkings(
    lineCount: Int,
    markingHeight: Dp,
    markingSpacing: Dp,
    speed: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val density = LocalDensity.current
    val markingHeightPx = with(density) { markingHeight.toPx() }
    val markingSpacingPx = with(density) { markingSpacing.toPx() }

    val offsetY by infiniteTransition.animateFloat(
        initialValue = markingHeightPx + markingSpacingPx,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (1000 / speed).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val markingColor = colorResource(id = R.color.white)

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val lineWidth = with(density) { 15.dp.toPx() }
        val laneWidth = canvasWidth / lineCount

        for (i in 0 until lineCount) {
            val laneStartX = laneWidth * i + (laneWidth - lineWidth) / 2

            val startYOffset = if (i % 2 == 0) 0f else markingHeightPx

            var currentY = -offsetY + startYOffset
            while (currentY < canvasHeight) {
                drawRect(
                    color = markingColor,
                    topLeft = Offset(laneStartX, currentY),
                    size = Size(lineWidth, markingHeightPx)
                )
                currentY += markingHeightPx + markingSpacingPx
            }
        }
    }
}