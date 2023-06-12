package com.czech.olympicathletes.ui.screens.athlete

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    viewModel: AthletesListViewModel
) {
    val athleteState by viewModel.athletesState.collectAsState()

    Content(
        state = athleteState,
        refresh = {viewModel.getAthletes()},
        onAthleteClicked = { athleteId ->
            onAthleteClicked(athleteId)
        }
    )
}

@Composable
private fun Content(
    state: AthleteListState,
    refresh: () -> Unit,
    onAthleteClicked: (String) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Text(
                text = "Olympic Athletes",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 24.sp,
                fontWeight = FontWeight.W700,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 14.dp, start = 16.dp)
                    .fillMaxWidth()
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            when (state) {
                is AthleteListState.Loading -> {
                    LoadingState(
                        text = "Fetching Athletes...",
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
                        btnText = "Try Again",
                        onClick = { refresh() },
                        modifier = Modifier
                    )
                }
            }
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
                            city = "Barcelona",
                            year = 2023
                        ),
                        athletes = (0..100).map {
                            Athletes(
                                athleteId = UUID.randomUUID().toString(),
                                name = "Name",
                                surname = "Surname",
                                bio = "",
                                dateOfBirth = "",
                                weight = 72,
                                height = 45,
                                photoId = 0

                            )
                        }
                    )
                }
            ),
            refresh = {},
            onAthleteClicked = {}
        )
    }
}