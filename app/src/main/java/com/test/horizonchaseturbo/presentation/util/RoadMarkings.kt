package com.test.horizonchaseturbo.presentation.util

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.Dp
import com.test.horizonchaseturbo.R

@Composable
fun RoadMarkings(
    lineCount: Int,
    lineWidth: Float,
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
        val laneWidth = canvasWidth / lineCount

        (0 until lineCount).forEach{ i ->
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