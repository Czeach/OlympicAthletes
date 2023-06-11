package com.czech.olympicathletes.di

import com.czech.olympicathletes.data.repository.AthleteDetailsRepository
import com.czech.olympicathletes.data.repository.AthleteDetailsRepositoryImpl
import com.czech.olympicathletes.data.repository.AthleteRepository
import com.czech.olympicathletes.data.repository.AthleteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
abstract class RepositoryModule {

    @[Binds Singleton]
    abstract fun provideAthleteRepositoryImpl(
        athleteRepositoryImpl: AthleteRepositoryImpl
    ): AthleteRepository

    @[Binds Singleton]
    abstract fun provideAthleteDetailsRepository(
        athleteDetailsRepositoryImpl: AthleteDetailsRepositoryImpl
    ): AthleteDetailsRepository
}