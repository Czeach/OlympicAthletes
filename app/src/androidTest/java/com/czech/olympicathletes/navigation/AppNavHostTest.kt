package com.czech.olympicathletes.navigation

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.czech.olympicathletes.MainActivity
import com.czech.olympicathletes.R
import com.czech.olympicathletes.testUtils.getString
import com.czech.olympicathletes.ui.navigation.AppNavHost
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Tests that navigation flow is handled
 */
@RunWith(JUnit4::class)
@HiltAndroidTest
class AppNavHostTest {

    /**
     * Manages the components' state perform injection on your test
     */
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    /**
     * initialise app using primary activity
     */
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    /**
     * strings used for matching
     */
    private val athleteListScreenTestTag by composeRule.getString(R.string.athlete_list_screen_test_tag)

    @Before
    fun setupAppNavHost() {
        hiltRule.inject()
        composeRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }
    }

    /**
     * launches appropriate start destination
     */
    @Test
    fun verifyAppNavHostStartDestination() {
        composeRule
            .onNodeWithTag(athleteListScreenTestTag)
            .assertIsDisplayed()
    }

}