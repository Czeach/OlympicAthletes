package com.czech.olympicathletes.network.models

data class AthleteWithResults(
    val athlete: Athletes,
    val athleteResults: List<AthleteResults>
)
