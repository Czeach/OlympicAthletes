package com.czech.olympicathletes.testUtils

import com.czech.olympicathletes.data.repository.AthleteDetailsRepository
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.network.models.Athletes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * fake athlete object
 */
val fakeAthlete = Athletes(
    athleteId = "",
    name = "",
    surname = "",
    dateOfBirth = "",
    bio = "",
    weight = 0,
    height = 0,
    photoId = 0,
    points = 0
)

/**
 * create fake athlete detail repositories for each DataState for testing
 */

class FakeLoadingAthleteDetailsRepositoryImpl : AthleteDetailsRepository {
    override fun getAthleteWithResults(athleteId: String): Flow<DataState<AthleteWithResults>> {
        return flow {
            emit(
                DataState.loading()
            )

        }
    }

    override suspend fun getAthleteDetails(athleteId: String): Athletes {
        return fakeAthlete
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return emptyList()
    }

}

class FakeErrorAthleteDetailsRepositoryImpl : AthleteDetailsRepository {
    override fun getAthleteWithResults(athleteId: String): Flow<DataState<AthleteWithResults>> {
        return flow {
            emit(
                DataState.error(message = "Error")
            )

        }
    }

    override suspend fun getAthleteDetails(athleteId: String): Athletes {
        return fakeAthlete
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return emptyList()
    }

}

class FakeSuccessAthleteDetailsRepositoryImpl : AthleteDetailsRepository {
    override fun getAthleteWithResults(athleteId: String): Flow<DataState<AthleteWithResults>> {
        return flow {
            emit(
                DataState.success(
                    data = AthleteWithResults(
                        athleteResults = emptyList(),
                        athlete = fakeAthlete
                    )
                )
            )

        }
    }

    override suspend fun getAthleteDetails(athleteId: String): Athletes {
        return fakeAthlete
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return emptyList()
    }

}