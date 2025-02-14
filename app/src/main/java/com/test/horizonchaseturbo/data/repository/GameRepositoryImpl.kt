package com.test.horizonchaseturbo.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract.Data
import com.test.horizonchaseturbo.data.DataConstants
import com.test.horizonchaseturbo.domain.repository.GameRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val context: Context
) : GameRepository {

    val pref = context.getSharedPreferences(DataConstants.SHARED_PREF_S, Context.MODE_PRIVATE)

    override fun getBestScore(): Int {
        return pref.getInt(DataConstants.BEST_SCORE_PREF, 0)
    }

    override fun setBestScore(newScore: Int) {
        pref.edit().putInt(DataConstants.BEST_SCORE_PREF, newScore).apply()
    }

}