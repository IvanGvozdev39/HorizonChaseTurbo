package com.test.horizonchaseturbo.domain.usecases

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.test.horizonchaseturbo.domain.model.PoliceCar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlin.random.Random

class SpawnPoliceCarsUseCase {
    fun execute(density: Density): Flow<PoliceCar> = channelFlow {
        while (isActive) {
            val randomDelay = Random.nextLong(300L, 700L)
            delay(randomDelay)

            val availableLanes = (1..3).shuffled()
            val newPoliceCarLane = availableLanes.first()
            val newPoliceCarOffsetY = with(density) { (-200).dp.toPx() }

            send(PoliceCar(lane = newPoliceCarLane, offsetY = newPoliceCarOffsetY))
        }
    }
}
