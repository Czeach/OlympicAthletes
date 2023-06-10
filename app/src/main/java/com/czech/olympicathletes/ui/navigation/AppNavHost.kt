package com.czech.olympicathletes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.czech.olympicathletes.ui.screens.athlete.AthletesListScreen
import com.czech.olympicathletes.ui.screens.athlete.AthletesListViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.AthletesListScreen.route
    ) {

        fun onBackPressed() {
            if (navController.previousBackStackEntry != null) navController.navigateUp()
        }

        composable(
            route = Screens.AthletesListScreen.route
        ) {
            val viewModel = hiltViewModel<AthletesListViewModel>()
            AthletesListScreen(
                viewModel = viewModel
            )
        }

        composable(
            route = Screens.AthleteDetailScreen.route
        ) {

        }
    }
}