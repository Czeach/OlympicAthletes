package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.Athletes
import kotlinx.coroutines.flow.Flow

interface AthleteDetailsRepository {

    fun getAthleteWithResults(athleteId: String): Flow<DataState<AthleteWithResults>>

    suspend fun getAthleteInfo(athleteId: String): Athletes

    suspend fun getAthleteResults(athleteId: String): List<AthleteResults>
}