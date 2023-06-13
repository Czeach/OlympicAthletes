package com.czech.olympicathletes.testUtils

import com.czech.olympicathletes.data.repository.AthletesRepository
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.network.models.Games
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLoadingAthletesRepositoryImpl(): AthletesRepository {
    override fun getGamesWithAthletes(): Flow<DataState<List<GameWithAthletes>>> {
        return flow {
            emit(DataState.loading())
        }
    }

    override suspend fun getGames(): List<Games> {
        return emptyList()
    }

    override suspend fun getAthletes(gameId: Int): List<Athletes> {
        return emptyList()
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return emptyList()
    }

}

class FakeSuccessAthletesRepositoryImpl(): AthletesRepository {
    override fun getGamesWithAthletes(): Flow<DataState<List<GameWithAthletes>>> {
        return flow {
            emit(
                DataState.success(data = listOf())
            )
        }
    }

    override suspend fun getGames(): List<Games> {
        return emptyList()
    }

    override suspend fun getAthletes(gameId: Int): List<Athletes> {
        return emptyList()
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return emptyList()
    }

}

class FakeErrorAthletesRepositoryImpl(): AthletesRepository {
    override fun getGamesWithAthletes(): Flow<DataState<List<GameWithAthletes>>> {
        return flow {
            emit(
                DataState.error(message = "Error")
            )
        }
    }

    override suspend fun getGames(): List<Games> {
        return emptyList()
    }

    override suspend fun getAthletes(gameId: Int): List<Athletes> {
        return emptyList()
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return emptyList()
    }

}