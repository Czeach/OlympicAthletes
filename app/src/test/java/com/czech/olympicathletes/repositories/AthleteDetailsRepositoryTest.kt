package com.czech.olympicathletes.repositories

import com.czech.olympicathletes.data.repository.AthleteDetailsRepositoryImpl
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.network.service.ApiService
import com.czech.olympicathletes.testUtils.CoroutinesRule
import com.czech.olympicathletes.testUtils.DataMocks.mockAthlete
import com.czech.olympicathletes.testUtils.DataMocks.mockAthleteResults
import com.czech.olympicathletes.testUtils.DataMocks.mockAthleteWithResults
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class AthleteDetailsRepositoryTest {

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private lateinit var repository: AthleteDetailsRepositoryImpl
    private val apiService = mockk<ApiService>()

    @Before
    fun setup() {

        // Initialize the repository with mocked ApiService and test dispatcher
        repository = AthleteDetailsRepositoryImpl(
            apiService,
            coroutinesRule.testDispatcher
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getAthleteWithResults() emits success when network call is successful`() = runTest {

        // Prepare mock data and responses
        val mockResponse = mockAthleteWithResults()
        val mockAthleteResults = mockAthleteResults()
        val mockAthlete = mockAthlete()

        // Mock the API service methods and return the mock data
        coEvery { apiService.getAthleteInfo(mockAthlete.athleteId) } returns mockAthlete
        coEvery { apiService.getAthleteResults(mockAthlete.athleteId) } returns mockAthleteResults

        // Invoke the repository method
        val result = repository.getAthleteWithResults(mockAthlete.athleteId)

        // Assert the result against the expected success state
        Assert.assertEquals(
            DataState.success(data = mockResponse),
            result.last()
        )
    }

    @Test
    fun `getAthleteWithResults() catches http exception and emits error`() = runTest {

        val mockAthlete = mockAthlete()
        val exception = HttpException(
            Response.error<ResponseBody>(
                404,
                "Error".toResponseBody(null)
            )
        )

        // Mock the API service method to throw the exception
        coEvery { apiService.getAthleteInfo(mockAthlete.athleteId) } throws exception

        val result = repository.getAthleteWithResults(mockAthlete.athleteId)

        Assert.assertEquals(
            DataState.error<AthleteWithResults>(message = exception.message ?: "Something went wrong"),
            result.last()
        )
    }

    @Test
    fun `getAthleteWithResults() catches io exception and emits error`() = runTest {

        val mockAthlete = mockAthlete()
        val exception = IOException()

        // Mock the API service method to throw the exception
        coEvery { apiService.getAthleteInfo(mockAthlete.athleteId) } throws exception

        val result = repository.getAthleteWithResults(mockAthlete.athleteId)

        Assert.assertEquals(
            DataState.error<AthleteWithResults>(message = exception.message ?: "Something went wrong"),
            result.last()
        )
    }
}