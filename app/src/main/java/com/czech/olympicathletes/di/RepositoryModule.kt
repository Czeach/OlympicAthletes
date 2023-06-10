package com.czech.olympicathletes.di

import com.czech.olympicathletes.data.repository.AthleteRepository
import com.czech.olympicathletes.data.repository.AthleteRepositoryImpl
import com.czech.olympicathletes.data.repository.GamesRepository
import com.czech.olympicathletes.data.repository.GamesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoryModule {

    @[Binds Singleton]
    abstract fun provideGamesRepositoryImpl(
        gamesRepositoryImpl: GamesRepositoryImpl
    ): GamesRepository

    @[Binds Singleton]
    abstract fun provideAthleteRepositoryImpl(
        athleteRepositoryImpl: AthleteRepositoryImpl
    ): AthleteRepository
}