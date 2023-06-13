package com.czech.olympicathletes.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.czech.olympicathletes.MainActivity
import com.czech.olympicathletes.data.repository.AthletesRepository
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.testUtils.FakeErrorAthletesRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeLoadingAthletesRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeSuccessAthletesRepositoryImpl
import com.czech.olympicathletes.ui.navigation.Screens
import com.czech.olympicathletes.ui.screens.athlete.AthletesListScreen
import com.czech.olympicathletes.ui.screens.athlete.AthletesListViewModel
import com.czech.olympicathletes.ui.theme.OlympicAthletesTheme
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AthletesListScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var athletesListViewModel: AthletesListViewModel

    private val fakeLoadingAthletesRepository = FakeLoadingAthletesRepositoryImpl()
    private val fakeErrorAthletesRepository = FakeErrorAthletesRepositoryImpl()
    private val fakeSuccessAthletesRepository = FakeSuccessAthletesRepositoryImpl()

    @Before
    fun setUp() {
        composeRule.activity.setContent {
            OlympicAthletesTheme {
                AthletesListScreen(
                    viewModel = athletesListViewModel,
                    onAthleteClicked = {},
                    modifier = Modifier
                )
            }
        }
    }

    private fun initViewModelForLoading() {
        athletesListViewModel = AthletesListViewModel(
            athletesRepository = fakeLoadingAthletesRepository
        )
    }

    private fun initViewModelForSuccess() {
        athletesListViewModel = AthletesListViewModel(
            athletesRepository = fakeSuccessAthletesRepository
        )
    }

    private fun initViewModelForError() {
        athletesListViewModel = AthletesListViewModel(
            athletesRepository = fakeErrorAthletesRepository
        )
    }

    @Test
    fun testAthleteListScreenDisplaysErrorState() {
        initViewModelForError()

        composeRule
            .onNodeWithTag("error_state_test_tag")
            .assertIsDisplayed()
    }

    @Test
    fun testAthleteListScreenDisplaysLoadingState() {
        initViewModelForLoading()

        composeRule
            .onNodeWithTag("loading_state_test_tag")
            .assertIsDisplayed()
    }

    @Test
    fun testAthleteListScreenDisplaysAthleteList() {
        initViewModelForSuccess()

        composeRule
            .onNodeWithTag("athlete_list_test_tag")
            .assertIsDisplayed()
    }

}