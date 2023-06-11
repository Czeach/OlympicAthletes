package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.AthleteInfo
import com.czech.olympicathletes.network.models.AthleteInfoWithResults
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.utils.DataState
import kotlinx.coroutines.flow.Flow

interface AthleteDetailsRepository {

    fun getAthleteInfoWithResults(athleteId: String): Flow<DataState<AthleteInfoWithResults>>

    suspend fun getAthleteInfo(athleteId: String): AthleteInfo

    suspend fun getAthleteResults(athleteId: String): List<AthleteResults>
}