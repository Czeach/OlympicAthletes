package com.czech.olympicathletes.repositories

import com.czech.olympicathletes.data.repository.AthletesRepositoryImpl
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.network.service.ApiService
import com.czech.olympicathletes.testUtils.CoroutinesRule
import com.czech.olympicathletes.testUtils.DataMocks.mockAthlete
import com.czech.olympicathletes.testUtils.DataMocks.mockAthleteResults
import com.czech.olympicathletes.testUtils.DataMocks.mockGame
import com.czech.olympicathletes.testUtils.DataMocks.mockGameWithAthletes
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class AthletesRepositoryTest {

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private val apiService = mockk<ApiService>()
    private lateinit var repository: AthletesRepositoryImpl

    @Before
    fun setup() {
        // Initialize the repository with mocked ApiService and test dispatcher

        repository = AthletesRepositoryImpl(
            apiService,
            coroutinesRule.testDispatcher
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAthleteInfoWithResults() emits success when network call is successful`() = runTest {

        // Prepare mock data and responses
        val mockResponse = listOf(mockGameWithAthletes())
        val mockAthleteResults = mockAthleteResults()
        val mockGame = mockGame()
        val mockAthlete = mockAthlete()

        // Mock the API service methods and return the mock data
        coEvery { apiService.getGames() } returns listOf(mockGame)
        coEvery { apiService.getAthletes(mockGame.gameId) } returns listOf(mockAthlete)
        coEvery { apiService.getAthleteResults(mockAthlete.athleteId) } returns mockAthleteResults

        // Call the repository method
        val result = repository.getGamesWithAthletes()

        // Assert the result against the expected error state
        assertEquals(
            DataState.success(data = mockResponse),
            result.last()
        )
    }

    @Test
    fun `getAthleteInfoWithResults() catches http exception and emits error`() = runTest {
        val exception = HttpException(
            Response.error<ResponseBody>(
                404,
                "Error".toResponseBody(null)
            )
        )

        // Mock the API service method to throw the exception
        coEvery { apiService.getGames() } throws exception

        val result = repository.getGamesWithAthletes()

        assertEquals(
            DataState.error<List<GameWithAthletes>>(message = exception.message ?: "Something went wrong"),
            result.last()
        )
    }

    @Test
    fun `getAthleteInfoWithResults() catches io exception and emits error`() = runTest {
        val exception = IOException()

        // Mock the API service method to throw the exception
        coEvery { apiService.getGames() } throws exception

        val result = repository.getGamesWithAthletes()

        assertEquals(
            DataState.error<List<GameWithAthletes>>(message = exception.message ?: "Something went wrong"),
            result.last()
        )
    }
}