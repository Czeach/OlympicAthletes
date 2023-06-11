package com.czech.olympicathletes.network.service

import com.czech.olympicathletes.network.models.AthleteInfo
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.Games
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("games")
    suspend fun getGames(
    ): List<Games>

    @GET("games/{id}/athletes")
    suspend fun getAthletes(
        @Path("id") gameId: Int
    ): List<Athletes>

    @GET("athletes/{id}/results")
    suspend fun getAthleteResults(
        @Path("id") athleteId: String,
    ): List<AthleteResults>

    @GET("athletes/{id}")
    suspend fun getAthleteInfo(
        @Path("id") athleteId: String
    ): AthleteInfo
}