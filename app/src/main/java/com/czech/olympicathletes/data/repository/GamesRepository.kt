package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.utils.DataState
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getGames(): Flow<DataState<List<Games>>>
}