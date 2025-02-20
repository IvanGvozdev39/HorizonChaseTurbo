package com.test.horizonchaseturbo.domain.usecases

class MovePlayerCarRightUseCase {
    operator fun invoke(playerLane: Int): Int {
        return if (playerLane < 3) playerLane + 1 else playerLane
    }
}