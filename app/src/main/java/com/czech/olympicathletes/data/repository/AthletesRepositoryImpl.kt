package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.network.service.ApiService
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.utils.calculateGamePoints
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AthletesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
): AthletesRepository {
    override fun getGamesWithAthletes(): Flow<DataState<List<GameWithAthletes>>> {
        return flow {
            emit(DataState.loading())

            try {
                val games = getGames()
                val gameWithAthletes = games.map { game ->
                    CoroutineScope(dispatcher).async {
                        val athletes = getAthletes(game.gameId)
                        val sortedAthletes = sortAthletes(
                            game,
                            athletes
                        )
                        GameWithAthletes(
                            game = game,
                            athletes = sortedAthletes
                        )
                    }
                }.awaitAll()

                emit(DataState.success(data = gameWithAthletes))

            } catch (e: IOException) {
                emit(DataState.error(message = e.message ?: "Something went wrong"))
            } catch (e: HttpException) {
                emit(DataState.error(message = "Error: ${e.code()}"))
            }
        }.flowOn(dispatcher)
    }

    private suspend fun sortAthletes(
        game: Games,
        athletes: List<Athletes>
    ): List<Athletes> {
        return withContext(dispatcher) {
            val gameResult = athletes.map { athlete ->
                 CoroutineScope(dispatcher).async {
                    getAthleteResults(athlete.athleteId).find { athleteResult ->
                        "${game.city} ${game.year}" == "${athleteResult.city} ${athleteResult.year}"
                    }
                }
            }.awaitAll()

            athletes.forEachIndexed { index, athlete ->
                athlete.points = calculateGamePoints(gameResult[index])
            }

            return@withContext athletes.sortedByDescending { it.points }
        }
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

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return withContext(dispatcher) {
            apiService.getAthleteResults(athleteId = athleteId)
        }
    }
}