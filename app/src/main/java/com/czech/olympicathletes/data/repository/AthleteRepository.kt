package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.utils.DataState
import kotlinx.coroutines.flow.Flow

interface AthleteRepository {
    fun getGamesWithAthletes(): Flow<DataState<List<GameWithAthletes>>>
    suspend fun getGames(): List<Games>

    suspend fun getAthletes(gameId: Int): List<Athletes>
}