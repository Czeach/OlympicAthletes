package com.czech.olympicathletes.network.models


data class AthleteResults(
    val city: String,
    val year: Int,
    val gold: Int,
    val silver: Int,
    val bronze: Int
)