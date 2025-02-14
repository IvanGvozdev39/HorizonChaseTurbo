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
    fun provideContext(@ApplicationContext appContext: Context): Context {
        return appContext
    }

    @Provides
    @Singleton
    fun provideGameRepository(context: Context): GameRepository {
        return GameRepositoryImpl(context)
    }
}