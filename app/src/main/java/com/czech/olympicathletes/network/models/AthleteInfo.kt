package com.czech.olympicathletes.network.models


import com.google.gson.annotations.SerializedName

data class AthleteInfo(
    @SerializedName("athlete_id")
    val athleteId: String,
    val name: String?,
    val surname: String?,
    val dateOfBirth: String?,
    val bio: String?,
    val weight: Int?,
    val height: Int?,
    @SerializedName("photo_id")
    val photoId: Int?
)