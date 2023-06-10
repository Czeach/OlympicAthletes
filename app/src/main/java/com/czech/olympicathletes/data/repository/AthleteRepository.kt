package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.utils.DataState
import kotlinx.coroutines.flow.Flow

interface AthleteRepository {
    fun getAthletes(gameId: Int): Flow<DataState<List<Athletes>>>
}