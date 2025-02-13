package com.test.horizonchaseturbo.data.di

import android.content.Context
import android.content.SharedPreferences
import com.test.horizonchaseturbo.data.DataConstants
import com.test.horizonchaseturbo.data.repository.GameRepositoryImpl
import com.test.horizonchaseturbo.domain.repository.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(DataConstants.SHARED_PREF_S, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideGameRepository(sharedPreferences: SharedPreferences): GameRepository {
        return GameRepositoryImpl(sharedPreferences)
    }
}