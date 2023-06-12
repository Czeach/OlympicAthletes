package com.czech.olympicathletes.repositories

import com.czech.olympicathletes.data.repository.AthleteRepositoryImpl
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
import org.junit.*
import org.junit.Assert.assertEquals
import retrofit2.HttpException
import retrofit2.Response

class AthleteRepositoryTest {

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private lateinit var repository: AthleteRepositoryImpl
    private val apiService = mockk<ApiService>()

    @Before
    fun setup() {
        repository = AthleteRepositoryImpl(
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
        val mockResponse = listOf(mockGameWithAthletes())
        val mockAthleteResults = mockAthleteResults()
        val mockGame = mockGame()
        val mockAthlete = mockAthlete()


        coEvery { apiService.getGames() } returns listOf(mockGame)
        coEvery { apiService.getAthletes(mockGame.gameId) } returns listOf(mockAthlete)
        coEvery { apiService.getAthleteResults(mockAthlete.athleteId) } returns mockAthleteResults

        val result = repository.getGamesWithAthletes()

        assertEquals(
            DataState.success(data = mockResponse),
            result.last()
        )
    }

    @Test
    fun `getAthleteInfoWithResults() catches exception and emits error when there's exception`() = runTest {
        val exception = HttpException(
            Response.error<ResponseBody>(
                404,
                "Error".toResponseBody(null)
            )
        )

        coEvery { apiService.getGames() } throws exception

        val result = repository.getGamesWithAthletes()

        assertEquals(
            DataState.error<List<GameWithAthletes>>(message = "Error: ${exception.code()}"),
            result.last()
        )
    }
}