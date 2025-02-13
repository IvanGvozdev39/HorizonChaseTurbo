package com.test.horizonchaseturbo.domain.usecases

import com.test.horizonchaseturbo.domain.repository.GameRepository
import javax.inject.Inject

class GetBestScoreUseCase @Inject constructor(
    private val repository: GameRepository
) {
    fun execute(): Int {
        return repository.getBestScore()
    }
}