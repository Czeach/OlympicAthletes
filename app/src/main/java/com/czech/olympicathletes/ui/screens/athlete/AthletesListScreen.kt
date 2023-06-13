package com.czech.olympicathletes.ui.screens.athlete

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czech.olympicathletes.R
import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.ui.components.ErrorState
import com.czech.olympicathletes.ui.components.GamesList
import com.czech.olympicathletes.ui.components.LoadingState
import com.czech.olympicathletes.ui.screens.states.AthleteListState
import com.czech.olympicathletes.ui.theme.OlympicAthletesTheme
import java.util.*
import kotlin.random.Random

@Composable
fun AthletesListScreen(
    onAthleteClicked: (String) -> Unit,
    viewModel: AthletesListViewModel,
    modifier: Modifier
) {
    val athleteState by viewModel.athletesState.collectAsState()
    val refreshing by viewModel.isRefreshing.collectAsState()

    Content(
        state = athleteState,
        tryAgain = {viewModel.getAthletes()},
        refresh = { viewModel.swipeDownToRefresh() },
        refreshing = refreshing,
        onAthleteClicked = { athleteId ->
            onAthleteClicked(athleteId)
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(
    state: AthleteListState,
    tryAgain: () -> Unit,
    refresh: () -> Unit,
    refreshing: Boolean,
    onAthleteClicked: (String) -> Unit,
    modifier: Modifier
) {
    val snackBarHostState = remember { SnackbarHostState() }

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refresh() })

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            Text(
                text = stringResource(R.string.olympic_athletes),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .padding(top = 14.dp, start = 16.dp)
                    .fillMaxWidth()
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .pullRefresh(pullRefreshState)
        ) {
            when (state) {
                is AthleteListState.Loading -> {
                    LoadingState(
                        text = stringResource(R.string.fetching_athletes),
                        modifier = Modifier
                    )
                }
                is AthleteListState.Success -> {
                    val games = state.data
                    games?.let { game ->
                        GamesList(
                            games = game,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            onAthleteClicked = { athleteId ->
                                onAthleteClicked(athleteId)
                            }
                        )
                    }
                }
                is AthleteListState.Error -> {
                    ErrorState(
                        message = state.message.toString(),
                        btnText = stringResource(R.string.try_again),
                        onClick = { tryAgain() },
                        modifier = Modifier
                    )
                }
            }

            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                backgroundColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
        }
    }
}


@Preview
@Composable
private fun AthletesListScreenPreview() {
    OlympicAthletesTheme {
        Content(
            state = AthleteListState.Success(
                data = (0..5).map {
                    GameWithAthletes(
                        game = Games(
                            gameId = Random.nextInt(),
                            city = stringResource(R.string.city),
                            year = 2023
                        ),
                        athletes = (0..100).map {
                            Athletes(
                                athleteId = UUID.randomUUID().toString(),
                                name = stringResource(R.string.name),
                                surname = stringResource(R.string.surname),
                                bio = stringResource(R.string.bio),
                                dateOfBirth = stringResource(R.string.dob),
                                weight = 72,
                                height = 45,
                                photoId = 0

                            )
                        }
                    )
                }
            ),
            tryAgain = {},
            onAthleteClicked = {},
            refreshing = false,
            refresh = {},
            modifier = Modifier
        )
    }
}