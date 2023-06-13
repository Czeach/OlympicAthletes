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

class AthleteDetailsRepositoryTest {

    @get:Rule
    val coroutinesRule = CoroutinesRule()

    private lateinit var repository: AthleteDetailsRepositoryImpl
    private val apiService = mockk<ApiService>()

    @Before
    fun setup() {
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

        val mockResponse = mockAthleteWithResults()
        val mockAthleteResults = mockAthleteResults()
        val mockAthlete = mockAthlete()

        coEvery { apiService.getAthleteInfo(mockAthlete.athleteId) } returns mockAthlete
        coEvery { apiService.getAthleteResults(mockAthlete.athleteId) } returns mockAthleteResults
        val result = repository.getAthleteWithResults(mockAthlete.athleteId)

        Assert.assertEquals(
            DataState.success(data = mockResponse),
            result.last()
        )
    }

    @Test
    fun `getAthleteWithResults() catches exception and emits error when there's exception`() = runTest {

        val mockAthlete = mockAthlete()
        val exception = HttpException(
            Response.error<ResponseBody>(
                404,
                "Error".toResponseBody(null)
            )
        )

        coEvery { apiService.getAthleteInfo(mockAthlete.athleteId) } throws exception

        val result = repository.getAthleteWithResults(mockAthlete.athleteId)

        Assert.assertEquals(
            DataState.error<AthleteWithResults>(message = "Error: ${exception.code()}"),
            result.last()
        )
    }
}