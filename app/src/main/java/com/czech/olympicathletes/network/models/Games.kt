package com.czech.olympicathletes.network.models


import com.google.gson.annotations.SerializedName

data class Games(
    @SerializedName("game_id")
    val gameId: Int?,
    val city: String?,
    val year: Int?
)