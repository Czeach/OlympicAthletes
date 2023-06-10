package com.czech.olympicathletes.network.service

import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.Games
import retrofit2.Response
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val apiService: ApiService
): ApiService {
    override suspend fun getGames(): Response<List<Games>> {
        return apiService.getGames()
    }

    override suspend fun getAthletes(gameId: Int): Response<List<Athletes>> {
        return apiService.getAthletes(gameId)
    }
}