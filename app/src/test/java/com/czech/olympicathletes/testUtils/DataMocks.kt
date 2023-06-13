package com.czech.olympicathletes.testUtils

import com.czech.olympicathletes.network.models.*

object DataMocks {

    fun mockAthlete() = Athletes(
        athleteId = "3",
        name = "",
        surname = "",
        dateOfBirth = "",
        bio = "",
        weight = 0,
        height = 0,
        photoId = 1
    )

    fun mockAthleteResults() = listOf(
        AthleteResults(
            city = "City",
            year = 2018,
            gold = 0,
            silver = 0,
            bronze = 0
        )
    )

    fun mockGame() = Games(
        gameId = 1,
        year = 2018,
        city = "City"
    )

    fun mockGameWithAthletes() = GameWithAthletes(
        game = mockGame(),
        athletes = listOf(mockAthlete())
    )

    fun mockAthleteWithResults() = AthleteWithResults(
        athlete = mockAthlete(),
        athleteResults = mockAthleteResults()
    )
}