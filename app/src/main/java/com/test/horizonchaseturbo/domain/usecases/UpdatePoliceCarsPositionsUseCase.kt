package com.test.horizonchaseturbo.domain.usecases

import com.test.horizonchaseturbo.domain.model.PoliceCar

class UpdatePoliceCarPositionsUseCase {
    fun execute(policeCars: MutableList<PoliceCar>, sizeHeight: Float, policeCarSpeed: Float) {
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