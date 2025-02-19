package com.test.horizonchaseturbo.domain.usecases

class MovePlayerCarLeftUseCase {
    fun execute(playerLane: Int): Int {
        return if (playerLane > 1) playerLane - 1 else playerLane
    }
}