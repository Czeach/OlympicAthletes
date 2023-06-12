package com.czech.olympicathletes.utils

import com.czech.olympicathletes.network.models.AthleteResults

fun calculateGamePoints(gameResult: AthleteResults?): Int {
    if (gameResult != null) {
        val goldPoints = gameResult.gold * 5
        val silverPoints = gameResult.silver * 3

        return goldPoints + silverPoints + gameResult.bronze
    }
    return 0
}