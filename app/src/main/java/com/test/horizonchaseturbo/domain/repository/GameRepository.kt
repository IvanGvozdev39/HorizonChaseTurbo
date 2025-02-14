package com.test.horizonchaseturbo.domain.repository

interface GameRepository {
    fun getBestScore(): Int
    fun setBestScore(newScore: Int)
}