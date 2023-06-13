package com.czech.olympicathletes.testUtils

import com.czech.olympicathletes.data.repository.AthleteDetailsRepository
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.network.models.Athletes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val fakeAthlete = Athletes(
    athleteId = "5",
    name = "Name",
    surname = "Surname",
    dateOfBirth = "6/4/1992",
    bio = "This is the bio here",
    weight = 10,
    height = 10,
    photoId = 40,
    points = 3
)

class FakeLoadingAthleteDetailsRepositoryImpl(): AthleteDetailsRepository {
    override fun getAthleteWithResults(athleteId: String): Flow<DataState<AthleteWithResults>> {
        return flow {
            emit(
                DataState.loading()
            )

        }
    }

    override suspend fun getAthleteInfo(athleteId: String): Athletes {
        return fakeAthlete
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return emptyList()
    }

}

class FakeErrorAthleteDetailsRepositoryImpl(): AthleteDetailsRepository {
    override fun getAthleteWithResults(athleteId: String): Flow<DataState<AthleteWithResults>> {
        return flow {
            emit(
                DataState.error(message = "Error")
            )

        }
    }

    override suspend fun getAthleteInfo(athleteId: String): Athletes {
        return fakeAthlete
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return emptyList()
    }

}

class FakeSuccessAthleteDetailsRepositoryImpl(): AthleteDetailsRepository {
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

    override suspend fun getAthleteInfo(athleteId: String): Athletes {
        return fakeAthlete
    }

    override suspend fun getAthleteResults(athleteId: String): List<AthleteResults> {
        return emptyList()
    }

}