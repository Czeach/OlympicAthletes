package com.czech.olympicathletes.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.lifecycle.SavedStateHandle
import com.czech.olympicathletes.MainActivity
import com.czech.olympicathletes.R
import com.czech.olympicathletes.testUtils.FakeErrorAthleteDetailsRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeLoadingAthleteDetailsRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeSuccessAthleteDetailsRepositoryImpl
import com.czech.olympicathletes.testUtils.getString
import com.czech.olympicathletes.ui.screens.detail.AthleteDetailScreen
import com.czech.olympicathletes.ui.screens.detail.AthleteDetailViewModel
import com.czech.olympicathletes.ui.theme.OlympicAthletesTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * tests that the appropriate UI is displayed for each data state in the athlete details screen
 */
@RunWith(JUnit4::class)
@HiltAndroidTest
class AthleteDetailScreenTest {

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

    private lateinit var athletesDetailsViewModel: AthleteDetailViewModel

    private lateinit var savedStateHandle: SavedStateHandle

    /**
     * create fake repositories
     */
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

    /**
     * create view model for loading
     */
    private fun initViewModelForLoading() {
        athletesDetailsViewModel = AthleteDetailViewModel(
            athleteDetailsRepository = fakeLoadingAthleteDetailsRepository,
            savedStateHandle = savedStateHandle
        )
    }

    /**
     * create view model for success
     */
    private fun initViewModelForSuccess() {
        athletesDetailsViewModel = AthleteDetailViewModel(
            athleteDetailsRepository = fakeSuccessAthleteDetailsRepository,
            savedStateHandle = savedStateHandle
        )
    }

    /**
     * create view model for error
     */
    private fun initViewModelForError() {
        athletesDetailsViewModel = AthleteDetailViewModel(
            athleteDetailsRepository = fakeErrorAthleteDetailsRepository,
            savedStateHandle = savedStateHandle
        )
    }

    /**
     * get strings used for matching in tests
     */
    private val errorStateTestTag by composeRule.getString(R.string.error_state_test_tag)
    private val loadingStateTestTag by composeRule.getString(R.string.loading_state_test_tag)
    private val athleteDetailsTestTag by composeRule.getString(R.string.athlete_details_test_tag)


    /**
     * display error state UI when view model emits error
     */
    @Test
    fun testAthleteDetailsScreenDisplaysErrorState() {
        initViewModelForError()

        composeRule
            .onNodeWithTag(errorStateTestTag, true)
            .assertIsDisplayed()
    }

    /**
     * display loading state UI when view model emits loading
     */
    @Test
    fun testAthleteDetailsScreenDisplaysLoadingState() {
        initViewModelForLoading()

        composeRule
            .onNodeWithTag(loadingStateTestTag)
            .assertIsDisplayed()
    }

    /**
     * display athlete details when view model emits success
     */
    @Test
    fun testAthleteDetailsScreenDisplaysAthleteDetails() {
        initViewModelForSuccess()

        composeRule
            .onNodeWithTag(athleteDetailsTestTag)
            .assertIsDisplayed()
    }
}