package com.czech.olympicathletes.network.models

data class GameWithAthletes(
    val game: Games,
    val athletes: List<Athletes>
)
