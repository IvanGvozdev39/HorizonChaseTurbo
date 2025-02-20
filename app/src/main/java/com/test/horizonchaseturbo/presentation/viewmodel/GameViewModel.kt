package com.test.horizonchaseturbo.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.test.horizonchaseturbo.Constants
import com.test.horizonchaseturbo.domain.model.PoliceCar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    val policeCars = mutableStateListOf<PoliceCar>()

    var playerXOffset by mutableFloatStateOf(0f)
    private val policeCarSpeed = 8f

    private fun calculateMaxOneWayMerge(laneCount: Int): Int {
        if (laneCount < 5) {
            throw IllegalArgumentException("Lane count cannot be less than 5")
        }
        return (laneCount - 5) / 2 + 1
    }

    val maxOneWayMerge = calculateMaxOneWayMerge(laneCount = Constants.LANE_COUNT)

    fun movePlayerCarLeft(laneWidthPx: Float) {
        if (playerXOffset > -laneWidthPx*maxOneWayMerge)
            playerXOffset -= laneWidthPx
    }

    fun movePlayerCarRight(laneWidthPx: Float) {
        if (playerXOffset < laneWidthPx*maxOneWayMerge)
            playerXOffset += laneWidthPx
    }

    private fun getRandomOffset(laneWidthPx: Float): Float {
        val steps = (maxOneWayMerge * 2) + 1
        val randomStep = Random.nextInt(steps) - maxOneWayMerge
        return randomStep * laneWidthPx
    }

    fun spawnPoliceCars(laneWidthPx: Float, density: Density): Flow<PoliceCar> = channelFlow {
        while (isActive) {
            val randomDelay = Random.nextLong(300L, 700L)
            delay(randomDelay)

            val newPoliceCarOffsetY = with(density) { (-200).dp.toPx() }
            val offsetX = getRandomOffset(laneWidthPx)

            send(PoliceCar(offsetX = offsetX, offsetY = newPoliceCarOffsetY))
        }
    }

    fun addPoliceCar(newPoliceCar: PoliceCar, density: Density) {
        if (policeCars.none { it.offsetY - newPoliceCar.offsetY < with(density) { 200.dp.toPx() } }) {
            policeCars.add(newPoliceCar)
        }
    }

    fun updatePoliceCarPositions(sizeHeight: Float) {
        val carsToRemove = mutableListOf<PoliceCar>()

        policeCars.forEach { policeCar ->
            policeCar.offsetY += policeCarSpeed

            if (policeCar.offsetY > sizeHeight) {
                carsToRemove.add(policeCar)
            }
        }

        policeCars.removeAll(carsToRemove)
    }
}