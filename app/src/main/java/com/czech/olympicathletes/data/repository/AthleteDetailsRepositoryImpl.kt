package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.AthleteInfo
import com.czech.olympicathletes.network.models.AthleteInfoWithResults
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.service.ApiService
import com.czech.olympicathletes.utils.DataState
import kotlinx.coroutines.CoroutineDispatcher
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
    override fun getAthleteInfoWithResults(athleteId: String): Flow<DataState<AthleteInfoWithResults>> {
        return flow {
            emit(DataState.loading())

            try {
                val athleteInfoWithResults = AthleteInfoWithResults(
                    athleteInfo = getAthleteInfo(athleteId),
                    athleteResults = getAthleteResults(athleteId)
                )

                emit(DataState.success(data = athleteInfoWithResults))

            } catch (e: IOException) {
                emit(DataState.error(message = e.message ?: "Something went wrong"))
            } catch (e: HttpException) {
                emit(DataState.error(message = "Error: ${e.code()}"))
            }
        }.flowOn(dispatcher)
    }

    override suspend fun getAthleteInfo(athleteId: String): AthleteInfo {
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