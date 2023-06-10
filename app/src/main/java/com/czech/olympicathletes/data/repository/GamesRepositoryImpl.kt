package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.network.service.ApiClient
import com.czech.olympicathletes.utils.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient,
    private val dispatcher: CoroutineDispatcher
): GamesRepository {
    override fun getGames(): Flow<DataState<List<Games>>> {
        return flow {
            emit(DataState.loading())

            try {
                val response = apiClient.getGames()
                val games = response.body()

                if (response.isSuccessful) {
                    emit(DataState.success(data = games))
                } else {
                    emit(DataState.error(message = response.message()))
                }

            } catch (e: IOException) {
                emit(DataState.error(message = e.message ?: "Something went wrong"))
            } catch (e: HttpException) {
                emit(DataState.error(message = "Error: ${e.code()}"))
            }
        }.flowOn(dispatcher)
    }
}