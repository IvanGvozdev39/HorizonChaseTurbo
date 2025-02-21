package com.test.horizonchaseturbo.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private val _policeCars = mutableListOf<PoliceCar>()

    val policeCars: List<PoliceCar>
        get() = _policeCars

    var carsPassedCounter by mutableIntStateOf(0)
    var gameOver by mutableStateOf(false)

    var playerXOffset by mutableFloatStateOf(0f)
    private var currentPlayerXOffset = 0f
    private val policeCarSpeed = 8f

    private fun calculateMaxOneWayMerge(laneCount: Int): Int {
        if (laneCount < 5) {
            throw IllegalArgumentException("Lane count cannot be less than 5")
        }
        return (laneCount - 5) / 2 + 1
    }

    private val maxOneWayMerge = calculateMaxOneWayMerge(laneCount = Constants.LANE_COUNT)

    fun movePlayerCarLeft(laneWidthPx: Float) {
        if (playerXOffset > -laneWidthPx * maxOneWayMerge) {
            playerXOffset -= laneWidthPx
            currentPlayerXOffset = playerXOffset
        }
    }

    fun movePlayerCarRight(laneWidthPx: Float) {
        if (playerXOffset < laneWidthPx * maxOneWayMerge) {
            playerXOffset += laneWidthPx
            currentPlayerXOffset = playerXOffset
        }
    }

    private fun getRandomOffset(laneWidthPx: Float): Float {
        val steps = (maxOneWayMerge * 2) + 1
        val randomStep = Random.nextInt(steps) - maxOneWayMerge
        return randomStep * laneWidthPx
    }

    fun spawnPoliceCars(laneWidthPx: Float, offsetY: Float): Flow<PoliceCar> = channelFlow {
        while (isActive) {
            val randomDelay = Random.nextLong(300L, 700L)
            delay(randomDelay)
            val offsetX = getRandomOffset(laneWidthPx)
            send(PoliceCar(offsetX = offsetX, offsetY = offsetY))
        }
    }

    fun addPoliceCar(newPoliceCar: PoliceCar, minYOffset: Float) {
        if (_policeCars.none { it.offsetY - newPoliceCar.offsetY < minYOffset }) {
            _policeCars.add(newPoliceCar)
        }
    }

    fun updatePoliceCarPositions(sizeHeight: Float, heightUntilPlayerCarCollision: Float, playerCarHeight: Float) {
        val carsToRemove = mutableListOf<PoliceCar>()

        _policeCars.forEachIndexed { index, policeCar ->
            _policeCars[index] = policeCar.copy(offsetY = policeCar.offsetY + policeCarSpeed)

            checkForCollision(_policeCars[index], heightUntilPlayerCarCollision, playerCarHeight)

            if (policeCar.offsetY > sizeHeight) {
                carsToRemove.add(policeCar)
            }
        }

        _policeCars.removeAll(carsToRemove)
    }

    private fun checkForCollision(policeCar: PoliceCar, heightUntilPlayerCarCollision: Float, playerCarHeight: Float) {
        val collisionDetected = policeCar.offsetY >= heightUntilPlayerCarCollision &&
                currentPlayerXOffset == policeCar.offsetX &&
                policeCar.offsetY < heightUntilPlayerCarCollision + playerCarHeight

        val updatedPoliceCar = if (collisionDetected && !policeCar.hasCollided) {
            policeCar.copy(hasCollided = true)
        } else if (policeCar.offsetY >= heightUntilPlayerCarCollision + playerCarHeight && !policeCar.passed) {
            carsPassedCounter++
            policeCar.copy(passed = true)
        } else {
            policeCar
        }

        val index = _policeCars.indexOf(policeCar)
        if (index != -1) {
            _policeCars[index] = updatedPoliceCar
        }

        if (collisionDetected && !policeCar.hasCollided) {
            gameOver = true
        }
    }
}
