package com.czech.olympicathletes.di

import com.czech.olympicathletes.data.repository.AthleteDetailsRepository
import com.czech.olympicathletes.data.repository.AthleteDetailsRepositoryImpl
import com.czech.olympicathletes.data.repository.AthletesRepository
import com.czech.olympicathletes.data.repository.AthletesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoryModule {

    @[Binds Singleton]
    abstract fun provideAthleteRepositoryImpl(
        athleteRepositoryImpl: AthletesRepositoryImpl
    ): AthletesRepository

    @[Binds Singleton]
    abstract fun provideAthleteDetailsRepository(
        athleteDetailsRepositoryImpl: AthleteDetailsRepositoryImpl
    ): AthleteDetailsRepository
}