package com.czech.olympicathletes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.ui.screens.athlete.AthletesListViewModel
import com.czech.olympicathletes.ui.screens.states.AthleteListState

@Composable
fun GamesList(
    games: List<Games>,
    listState: LazyListState,
    viewModel: AthletesListViewModel,
    modifier: Modifier
) {
    LazyColumn(
        state = listState
    ) {
        items(
            items = games
        ) { game ->
            GameItem(
                modifier = Modifier,
                game = game,
//                athletes = athletes
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun GameItem(
    modifier: Modifier,
    game: Games,
    viewModel: AthletesListViewModel
) {

    val athletesState = viewModel.athletesState.collectAsState().value

    LaunchedEffect(key1 = game) {
        game.gameId?.let { viewModel.getAthletes(it) }
    }
    Column(
        modifier = modifier
            .padding(bottom = 20.dp)
    ) {
        Text(
            text = "${game.city} ${game.year}",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 15.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier
        )
        Spacer(
            modifier = Modifier
                .height(4.dp)
        )
        when (athletesState) {
            is AthleteListState.Loading -> {

            }
            is AthleteListState.Success -> {
                val athletes = athletesState.data

                if (athletes != null) {

                    AthletesList(
                        athletes = athletes
                    )
                }
            }
            is AthleteListState.Error -> {

            }
            null -> {

            }
        }
//        LazyRow(
//            modifier = Modifier
//                .fillMaxSize()
//
//        ) {
//            items(
//                items = athletes
//            ) { athletes ->
//                AthleteItem(
//                    modifier = Modifier
//                        .padding(end = 4.dp),
//                    athleteName = "${athletes.name} ${athletes.surname}"
//                )
//            }
//        }
    }
}