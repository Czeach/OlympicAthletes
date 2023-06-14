package com.czech.olympicathletes.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.ui.components.AthleteDetails
import com.czech.olympicathletes.ui.components.ErrorState
import com.czech.olympicathletes.ui.components.LoadingState
import com.czech.olympicathletes.ui.screens.states.AthleteDetailsState
import com.czech.olympicathletes.ui.theme.OlympicAthletesTheme
import java.util.*
import kotlin.random.Random

@Composable
fun AthleteDetailScreen(
    onBackPressed: () -> Unit,
    viewModel: AthleteDetailViewModel,
    modifier: Modifier
) {
    val detailsState by viewModel.detailsState.collectAsState()
    val refreshing by viewModel.isRefreshing.collectAsState()

    Content(
        state = detailsState,
        onBackPressed = { onBackPressed() },
        tryAgain = { viewModel.getAthleteDetails() },
        refresh = { viewModel.swipeDownToRefresh() },
        refreshing = refreshing,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Content(
    state: AthleteDetailsState,
    onBackPressed: () -> Unit,
    tryAgain: () -> Unit,
    refresh: () -> Unit,
    refreshing: Boolean,
    modifier: Modifier
) {
    val snackBarHostState = remember { SnackbarHostState() }

    // implement pull down to refresh
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = { refresh() })

    val title = remember {
        mutableStateOf("")
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .padding(top = 6.dp, bottom = 6.dp)
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onBackPressed() },
                    modifier = Modifier
                        .wrapContentSize()
                        .testTag(stringResource(R.string.back_button))
                ) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
                Text(
                    text = title.value,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .fillMaxWidth()
                )
            }

        },
        modifier = modifier
            .fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .pullRefresh(pullRefreshState)
        ) {

            // update UI state
            when (state) {
                is AthleteDetailsState.Loading -> {
                    LoadingState(
                        text = stringResource(R.string.fetching_details),
                        modifier = Modifier
                            .testTag(stringResource(R.string.loading_state_test_tag))
                    )
                }
                is AthleteDetailsState.Success -> {
                    val athleteDetails = state.data

                    val info = athleteDetails?.athlete

                    title.value = "${info?.name} ${info?.surname} Details"

                    athleteDetails?.let { details ->
                        AthleteDetails(
                            athleteDetails = details,
                            modifier = Modifier
                                .padding(start = 10.dp, end = 10.dp, top = 6.dp)
                                .testTag(stringResource(R.string.athlete_details_test_tag))
                        )
                    }
                }
                is AthleteDetailsState.Error -> {
                    ErrorState(
                        message = state.message.toString(),
                        btnText = stringResource(R.string.try_again),
                        onClick = { tryAgain() },
                        modifier = Modifier
                            .testTag(stringResource(R.string.error_state_test_tag))
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


/**
 * athlete list screen preview
 */
@Preview
@Composable
fun AthleteDetailScreenPreview() {
    OlympicAthletesTheme {
        Content(
            state = AthleteDetailsState.Success(
                data = AthleteWithResults(
                    athlete = Athletes(
                        UUID.randomUUID().toString(),
                        name = stringResource(R.string.name),
                        surname = stringResource(R.string.surname),
                        dateOfBirth = stringResource(R.string.dob),
                        bio = stringResource(R.string.bio),
                        weight = 56,
                        height = 174,
                        photoId = Random.nextInt(),
                    ),
                    athleteResults = (0..5).map {
                        AthleteResults(
                            city = stringResource(R.string.city),
                            year = 2023,
                            gold = 5,
                            silver = 3,
                            bronze = 1
                        )
                    }
                )
            ),
            onBackPressed = {},
            tryAgain = {},
            refreshing = false,
            refresh = {},
            modifier = Modifier
        )
    }
}