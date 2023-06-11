package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.network.service.ApiService
import com.czech.olympicathletes.utils.DataState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AthleteRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
): AthleteRepository {
    override fun getGamesWithAthletes(): Flow<DataState<List<GameWithAthletes>>> {
        return flow {
            emit(DataState.loading())

            try {
                val games = getGames()
                val gameWithAthletes = games.map { game ->
                    val athletes = getAthletes(game.gameId)

                    GameWithAthletes(
                        games = game,
                        athletes = athletes
                    )
                }

                emit(DataState.success(data = gameWithAthletes))

            } catch (e: IOException) {
                emit(DataState.error(message = e.message ?: "Something went wrong"))
            } catch (e: HttpException) {
                emit(DataState.error(message = "Error: ${e.code()}"))
            }
        }.flowOn(dispatcher)
    }

    override suspend fun getGames(): List<Games> {
        return withContext(dispatcher) {
            apiService.getGames()
        }
    }

    override suspend fun getAthletes(gameId: Int): List<Athletes> {
        return withContext(dispatcher) {
            apiService.getAthletes(gameId = gameId)
        }
    }
}