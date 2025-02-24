package com.test.horizonchaseturbo.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.test.horizonchaseturbo.data.DataConstants
import com.test.horizonchaseturbo.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    context: Context
) : GameRepository {

    private val pref: SharedPreferences = context.getSharedPreferences(DataConstants.SHARED_PREF_S, Context.MODE_PRIVATE)

    override fun getBestScore(): Int {
        return pref.getInt(DataConstants.BEST_SCORE_PREF, 0)
    }

    override fun setBestScore(newScore: Int) {
        pref.edit().putInt(DataConstants.BEST_SCORE_PREF, newScore).apply()
    }

}