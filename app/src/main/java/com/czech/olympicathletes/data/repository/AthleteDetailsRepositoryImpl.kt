package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.service.ApiService
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.Athletes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import java.lang.Exception
import javax.inject.Inject

/**
 * Repository implementation that implements the methods in [AthleteDetailsRepository].
 * network operations are main-safe.
 */
class AthleteDetailsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
): AthleteDetailsRepository {
    override fun getAthleteWithResults(athleteId: String): Flow<DataState<AthleteWithResults>> {
        return flow {
            emit(DataState.loading())

            try {
                supervisorScope {
                    val athleteInfo = async { getAthleteDetails(athleteId) }
                    val athleteResults = async { getAthleteResults(athleteId) }

                    val athleteWithResults = AthleteWithResults(
                        athlete = athleteInfo.await(),
                        athleteResults = athleteResults.await()
                    )

                    emit(DataState.success(data = athleteWithResults))
                }
            } catch (e: Exception) {
                emit(DataState.error(message = e.message ?: "Something went wrong"))
            }
        }.flowOn(dispatcher)
    }

    /**
     *  network operation to get athlete details
     */
    override suspend fun getAthleteDetails(athleteId: String): Athletes {
        return withContext(dispatcher) {
            apiService.getAthleteInfo(athleteId = athleteId)
        }
    }

    /**
     *  network operation to get list of athlete results
     */
    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return withContext(dispatcher) {
            apiService.getAthleteResults(athleteId = athleteId)
        }
    }
}