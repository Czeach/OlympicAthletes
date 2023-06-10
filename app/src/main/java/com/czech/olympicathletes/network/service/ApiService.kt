package com.czech.olympicathletes.network.service

import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.Games
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("games")
    suspend fun getGames(
    ): Response<List<Games>>

    @GET("/games/{id}/athletes")
    suspend fun getAthletes(
        @Path("id") gameId: Int,
    ): Response<List<Athletes>>
}