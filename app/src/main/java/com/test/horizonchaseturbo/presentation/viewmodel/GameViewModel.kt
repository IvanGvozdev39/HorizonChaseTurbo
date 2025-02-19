package com.test.horizonchaseturbo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import com.test.horizonchaseturbo.domain.model.PoliceCar
import com.test.horizonchaseturbo.domain.usecases.AddPoliceCarUseCase
import com.test.horizonchaseturbo.domain.usecases.SpawnPoliceCarsUseCase
import com.test.horizonchaseturbo.domain.usecases.UpdatePoliceCarPositionsUseCase
import androidx.compose.ui.unit.Density
import com.test.horizonchaseturbo.domain.usecases.MovePlayerCarLeftUseCase
import com.test.horizonchaseturbo.domain.usecases.MovePlayerCarRightUseCase
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val spawnPoliceCarsUseCase: SpawnPoliceCarsUseCase,
    private val addPoliceCarUseCase: AddPoliceCarUseCase,
    private val updatePoliceCarPositionsUseCase: UpdatePoliceCarPositionsUseCase,
    private val movePlayerCarLeftUseCase: MovePlayerCarLeftUseCase,
    private val movePlayerCarRightUseCase: MovePlayerCarRightUseCase
) : ViewModel() {
    var playerLane by mutableIntStateOf(2)
    val policeCars = mutableStateListOf<PoliceCar>()
    private val policeCarSpeed = 8f

    fun movePlayerCarLeft() {
        playerLane = movePlayerCarLeftUseCase.execute(playerLane)
    }

    fun movePlayerCarRight() {
        playerLane = movePlayerCarRightUseCase.execute(playerLane)
    }

    fun spawnPoliceCars(density: Density) = spawnPoliceCarsUseCase.execute(density)

    fun addPoliceCar(newPoliceCar: PoliceCar, density: Density) {
        addPoliceCarUseCase.execute(policeCars, newPoliceCar, density)
    }

    fun updatePoliceCarPositions(sizeHeight: Float) {
        updatePoliceCarPositionsUseCase.execute(policeCars, sizeHeight, policeCarSpeed)
    }
}
