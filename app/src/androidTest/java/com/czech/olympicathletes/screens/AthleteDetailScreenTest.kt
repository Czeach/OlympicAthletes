package com.czech.olympicathletes.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.czech.olympicathletes.MainActivity
import com.czech.olympicathletes.testUtils.FakeErrorAthleteDetailsRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeErrorAthletesRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeLoadingAthleteDetailsRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeLoadingAthletesRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeSuccessAthleteDetailsRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeSuccessAthletesRepositoryImpl
import com.czech.olympicathletes.ui.screens.athlete.AthletesListScreen
import com.czech.olympicathletes.ui.screens.athlete.AthletesListViewModel
import com.czech.olympicathletes.ui.screens.detail.AthleteDetailScreen
import com.czech.olympicathletes.ui.screens.detail.AthleteDetailViewModel
import com.czech.olympicathletes.ui.theme.OlympicAthletesTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AthleteDetailScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var athletesDetailsViewModel: AthleteDetailViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    private val fakeLoadingAthleteDetailsRepository = FakeLoadingAthleteDetailsRepositoryImpl()
    private val fakeErrorAthleteDetailsRepository = FakeErrorAthleteDetailsRepositoryImpl()
    private val fakeSuccessAthleteDetailsRepository = FakeSuccessAthleteDetailsRepositoryImpl()

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle()
        savedStateHandle["athleteId"] = "2"
        composeRule.activity.setContent {
            OlympicAthletesTheme {
                AthleteDetailScreen(
                    viewModel = athletesDetailsViewModel,
                    onBackPressed = {},
                    modifier = Modifier
                )
            }
        }
    }

    private fun initViewModelForLoading() {
        athletesDetailsViewModel = AthleteDetailViewModel(
            athleteDetailsRepository = fakeLoadingAthleteDetailsRepository,
            savedStateHandle = savedStateHandle
        )
    }

    private fun initViewModelForSuccess() {
        athletesDetailsViewModel = AthleteDetailViewModel(
            athleteDetailsRepository = fakeSuccessAthleteDetailsRepository,
            savedStateHandle = savedStateHandle
        )
    }

    private fun initViewModelForError() {
        athletesDetailsViewModel = AthleteDetailViewModel(
            athleteDetailsRepository = fakeErrorAthleteDetailsRepository,
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun testAthleteDetailsScreenDisplaysErrorState() {
        initViewModelForError()

        composeRule
            .onNodeWithTag("error_state_test_tag", true)
            .assertIsDisplayed()
    }

    @Test
    fun testAthleteDetailsScreenDisplaysLoadingState() {
        initViewModelForLoading()

        composeRule
            .onNodeWithTag("loading_state_test_tag")
            .assertIsDisplayed()
    }

    @Test
    fun testAthleteDetailsScreenDisplaysAthleteDetails() {
        initViewModelForSuccess()

        composeRule
            .onNodeWithTag("athlete_details_test_tag")
            .assertIsDisplayed()
    }
}