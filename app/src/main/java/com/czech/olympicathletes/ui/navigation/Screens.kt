package com.czech.olympicathletes.ui.navigation

/**
 * app destinations
 */
sealed class Screens(val route: String) {
    object AthletesListScreen: Screens("athletes_list_screen")
    object AthleteDetailScreen: Screens("athlete_details_screen")
}