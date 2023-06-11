package com.czech.olympicathletes.network.models

data class GameWithAthletes(
    val games: Games,
    val athletes: List<Athletes>
)
