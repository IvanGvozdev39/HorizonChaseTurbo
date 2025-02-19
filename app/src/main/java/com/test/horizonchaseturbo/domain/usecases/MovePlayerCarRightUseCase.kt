package com.test.horizonchaseturbo.domain.usecases

class MovePlayerCarRightUseCase {
    fun execute(playerLane: Int): Int {
        return if (playerLane < 3) playerLane + 1 else playerLane
    }
}