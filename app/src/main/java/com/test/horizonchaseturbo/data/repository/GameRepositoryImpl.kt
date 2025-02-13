package com.test.horizonchaseturbo.data.repository

import android.content.SharedPreferences
import com.test.horizonchaseturbo.data.DataConstants
import com.test.horizonchaseturbo.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val pref: SharedPreferences
) : GameRepository {
    override fun getBestScore(): Int {
        return pref.getInt(DataConstants.BEST_SCORE_PREF, 0)
    }
}