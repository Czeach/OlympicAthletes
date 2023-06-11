package com.czech.olympicathletes.network.models

data class AthleteInfoWithResults(
    val athleteInfo: AthleteInfo,
    val athleteResults: List<AthleteResults>
)
