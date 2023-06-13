package com.czech.olympicathletes.navigation

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.czech.olympicathletes.MainActivity
import com.czech.olympicathletes.R
import com.czech.olympicathletes.ui.navigation.AppNavHost
import com.czech.olympicathletes.ui.navigation.Screens
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class AppNavHostTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }
    }

    @Test
    fun verifyAppNavHostStartDestination() {
        composeRule
            .onNodeWithTag("athlete_list_screen_test_tag")
            .assertIsDisplayed()
    }

    @Test
    fun verifyNavigateToDetailScreen() {
        composeRule
            .onNodeWithTag("athlete_image")
            .assertExists()

        val route = navController.currentDestination?.route
        assertEquals(
            route,
            Screens.AthleteDetailScreen.route + "/{athleteId}"
        )
    }

    @Test
    fun verifyNavigateBackToAthleteListScreen() {
        composeRule
            .onNodeWithTag("back_button")
            .performClick()

        val route = navController.currentDestination?.route
        assertEquals(
            route,
            Screens.AthletesListScreen.route
        )
    }

}