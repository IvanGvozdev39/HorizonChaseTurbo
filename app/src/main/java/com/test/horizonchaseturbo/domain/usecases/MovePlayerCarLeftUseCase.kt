package com.test.horizonchaseturbo.domain.usecases

class MovePlayerCarLeftUseCase {
    operator fun invoke(playerLane: Int): Int {
        return if (playerLane > 1) playerLane - 1 else playerLane
    }
}