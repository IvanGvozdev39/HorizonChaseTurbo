package com.test.horizonchaseturbo.domain.usecases

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.test.horizonchaseturbo.domain.model.PoliceCar

class AddPoliceCarUseCase {
    fun execute(policeCars: MutableList<PoliceCar>, newPoliceCar: PoliceCar, density: Density) {
        if (policeCars.none { it.offsetY - newPoliceCar.offsetY < with(density) { 200.dp.toPx() } }) {
            policeCars.add(newPoliceCar)
        }
    }
}