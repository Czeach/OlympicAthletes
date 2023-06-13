package com.czech.olympicathletes.viewModels

import com.czech.olympicathletes.data.repository.AthletesRepositoryImpl
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.testUtils.CoroutinesRule
import com.czech.olympicathletes.testUtils.DataMocks.mockGameWithAthletes
import com.czech.olympicathletes.ui.screens.athlete.AthletesListViewModel
import com.czech.olympicathletes.ui.screens.states.AthleteListState
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AthletesListViewModelTest {

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private val repository = mockk<AthletesRepositoryImpl>()
    private lateinit var viewModel: AthletesListViewModel

    @Before
    fun setup() {
        viewModel = AthletesListViewModel(
            repository
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }


    @Test
    fun testGetAthletes() = runTest(coroutinesRule.testDispatcher) {

        val mockGameWithAthletes = mockGameWithAthletes()

        coEvery { repository.getGamesWithAthletes() } returns flow {
            emit(
                DataState.success(
                    data = listOf(mockGameWithAthletes)
                )
            )
        }

        viewModel.getAthletes()

        val state = viewModel.athletesState

        assertEquals(
            AthleteListState.Success(data = listOf(mockGameWithAthletes)),
            state.value
        )
    }
}