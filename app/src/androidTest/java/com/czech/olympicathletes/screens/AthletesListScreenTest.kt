package com.czech.olympicathletes.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.czech.olympicathletes.MainActivity
import com.czech.olympicathletes.R
import com.czech.olympicathletes.testUtils.FakeErrorAthletesRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeLoadingAthletesRepositoryImpl
import com.czech.olympicathletes.testUtils.FakeSuccessAthletesRepositoryImpl
import com.czech.olympicathletes.testUtils.getString
import com.czech.olympicathletes.ui.screens.athlete.AthletesListScreen
import com.czech.olympicathletes.ui.screens.athlete.AthletesListViewModel
import com.czech.olympicathletes.ui.theme.OlympicAthletesTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * tests that the appropriate UI is displayed for each data state in the athletes list screen
 */
@RunWith(JUnit4::class)
@HiltAndroidTest
class AthletesListScreenTest {

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

    private lateinit var athletesListViewModel: AthletesListViewModel

    /**
     * create fake repositories
     */
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

    /**
     * create view model for loading
     */
    private fun initViewModelForLoading() {
        athletesListViewModel = AthletesListViewModel(
            athletesRepository = fakeLoadingAthletesRepository
        )
    }

    /**
     * create view model for success
     */
    private fun initViewModelForSuccess() {
        athletesListViewModel = AthletesListViewModel(
            athletesRepository = fakeSuccessAthletesRepository
        )
    }

    /**
     * create view model for error
     */
    private fun initViewModelForError() {
        athletesListViewModel = AthletesListViewModel(
            athletesRepository = fakeErrorAthletesRepository
        )
    }

    /**
     * get strings used for matching in tests
     */
    private val errorStateTestTag by composeRule.getString(R.string.error_state_test_tag)
    private val loadingStateTestTag by composeRule.getString(R.string.loading_state_test_tag)
    private val athleteListTestTag by composeRule.getString(R.string.athlete_list_test_tag)


    /**
     * display error state UI when view model emits error
     */
    @Test
    fun testAthleteListScreenDisplaysErrorState() {
        initViewModelForError()

        composeRule
            .onNodeWithTag(errorStateTestTag)
            .assertIsDisplayed()
    }

    /**
     * display loading state UI when view model emits loading
     */
    @Test
    fun testAthleteListScreenDisplaysLoadingState() {
        initViewModelForLoading()

        composeRule
            .onNodeWithTag(loadingStateTestTag)
            .assertIsDisplayed()
    }

    /**
     * display athletes list when view model emits success
     */
    @Test
    fun testAthleteListScreenDisplaysAthleteList() {
        initViewModelForSuccess()

        composeRule
            .onNodeWithTag(athleteListTestTag)
            .assertIsDisplayed()
    }

}