package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.Athletes
import kotlinx.coroutines.flow.Flow

/**
 * interface defining methods to load data from the backend
 */
interface AthleteDetailsRepository {

    fun getAthleteWithResults(athleteId: String): Flow<DataState<AthleteWithResults>>

    suspend fun getAthleteDetails(athleteId: String): Athletes

    suspend fun getAthleteResults(athleteId: String): List<AthleteResults>
}