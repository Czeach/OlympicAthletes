package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.service.ApiService
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.Athletes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AthleteDetailsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
): AthleteDetailsRepository {
    override fun getAthleteWithResults(athleteId: String): Flow<DataState<AthleteWithResults>> {
        return flow {
            emit(DataState.loading())

            try {
                val athleteInfo = CoroutineScope(dispatcher).async { getAthleteInfo(athleteId) }
                val athleteResults = CoroutineScope(dispatcher).async { getAthleteResults(athleteId) }

                val athleteWithResults = AthleteWithResults(
                    athlete = athleteInfo.await(),
                    athleteResults = athleteResults.await()
                )

                emit(DataState.success(data = athleteWithResults))

            } catch (e: IOException) {
                emit(DataState.error(message = e.message ?: "Something went wrong"))
            } catch (e: HttpException) {
                emit(DataState.error(message = "Error: ${e.code()}"))
            }
        }.flowOn(dispatcher)
    }

    override suspend fun getAthleteInfo(athleteId: String): Athletes {
        return withContext(dispatcher) {
            apiService.getAthleteInfo(athleteId = athleteId)
        }
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return withContext(dispatcher) {
            apiService.getAthleteResults(athleteId = athleteId)
        }
    }
}