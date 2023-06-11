package com.czech.olympicathletes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.czech.olympicathletes.ui.screens.athlete.AthletesListScreen
import com.czech.olympicathletes.ui.screens.athlete.AthletesListViewModel
import com.czech.olympicathletes.ui.screens.detail.AthleteDetailScreen
import com.czech.olympicathletes.ui.screens.detail.AthleteDetailViewModel

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
                viewModel = viewModel,
                onAthleteClicked = { athleteId ->
                    navController.navigate(Screens.AthleteDetailScreen.route + "/$athleteId")
                }
            )
        }

        composable(
            route = Screens.AthleteDetailScreen.route + "/{athleteId}",
            arguments = listOf(
                navArgument("athleteId") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            val backStackEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(Screens.AthleteDetailScreen.route + "/{athleteId}")
            }

            val viewModel = hiltViewModel<AthleteDetailViewModel>(backStackEntry)

            AthleteDetailScreen(
                onBackPressed = { onBackPressed() },
                viewModel = viewModel
            )
        }
    }
}