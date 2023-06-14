package com.czech.olympicathletes.data.repository

import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.network.service.ApiService
import com.czech.olympicathletes.utils.calculateGamePoints
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository implementation that implements the methods in [AthletesRepository]
 * network operations are main-safe.
 */
class AthletesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcher: CoroutineDispatcher
) : AthletesRepository {
    override fun getGamesWithAthletes(): Flow<DataState<List<GameWithAthletes>>> {
        return flow {
            emit(DataState.loading())

            try {
                val games = getGames()
                val gameWithAthletes = getGameWithAthletes(games)

                emit(DataState.success(data = gameWithAthletes))

            } catch (e: Exception) {
                emit(DataState.error(message = e.message ?: "Something went wrong"))
            }
        }.flowOn(dispatcher)
    }

    private suspend fun getGameWithAthletes(games: List<Games>): List<GameWithAthletes> =
        supervisorScope {
            games.map { game ->
                async {
                    val athletes = getAthletes(game.gameId)
                    val sortedAthletes = sortAthletes(game, athletes)

                    GameWithAthletes(game = game, athletes = sortedAthletes)
                }
            }.awaitAll()
        }

    /**
     * function to sort athletes under each game in descending order based on their points
     */
    private suspend fun sortAthletes(
        game: Games,
        athletes: List<Athletes>
    ): List<Athletes> {
        return withContext(dispatcher) {
            val gameResult = athletes.map { athlete ->
                CoroutineScope(dispatcher).async {
                    getAthleteResults(athlete.athleteId).find { athleteResult ->
                        "${game.city} ${game.year}" == "${athleteResult.city}${athleteResult.year}"
                    }
                }
            }.awaitAll()

            athletes.forEachIndexed { index, athlete ->
                athlete.points = calculateGamePoints(gameResult[index])
            }

            return@withContext athletes.sortedByDescending { it.points }
        }
    }

    /**
     * network operation to get list of games
     */
    override suspend fun getGames(): List<Games> = withContext(dispatcher) {
        apiService.getGames()
    }

    /**
     *  network operation to get list of athletes
     */
    override suspend fun getAthletes(gameId: Int): List<Athletes> = withContext(dispatcher) {
        apiService.getAthletes(gameId = gameId)
    }

    /**
     *  network operation to get list of athlete results
     */
    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> =
        withContext(dispatcher) {
            apiService.getAthleteResults(athleteId = athleteId)
        }
}