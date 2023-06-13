package com.czech.olympicathletes.viewModels

import androidx.lifecycle.SavedStateHandle
import com.czech.olympicathletes.data.repository.AthleteDetailsRepositoryImpl
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.testUtils.CoroutinesRule
import com.czech.olympicathletes.testUtils.DataMocks.mockAthlete
import com.czech.olympicathletes.testUtils.DataMocks.mockAthleteWithResults
import com.czech.olympicathletes.ui.screens.detail.AthleteDetailViewModel
import com.czech.olympicathletes.ui.screens.states.AthleteDetailsState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AthleteDetailViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private val repository = mockk<AthleteDetailsRepositoryImpl>()
    private lateinit var viewModel: AthleteDetailViewModel
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle()
        savedStateHandle["athleteId"] = "3"
        viewModel = AthleteDetailViewModel(
            savedStateHandle,
            repository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun testGetAthleteDetails() = runTest {

        val mockAthleteWithResults = mockAthleteWithResults()
        val mockAthlete = mockAthlete()

        coEvery { repository.getAthleteWithResults(mockAthlete.athleteId) } returns flow {
            emit(
                DataState.success(
                    data = mockAthleteWithResults()
                )
            )
        }

        viewModel.getAthleteDetails()

        val state = viewModel.detailsState

        Assert.assertEquals(
            AthleteDetailsState.Success(data = mockAthleteWithResults),
            state.value
        )
    }
}