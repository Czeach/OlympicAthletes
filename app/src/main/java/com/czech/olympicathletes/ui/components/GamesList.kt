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
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.network.models.Games
import com.czech.olympicathletes.ui.screens.athlete.AthletesListViewModel
import com.czech.olympicathletes.ui.screens.states.AthleteListState

@Composable
fun GamesList(
    games: List<GameWithAthletes>,
    onAthleteClicked: (String) -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
    ) {
        items(
            items = games,
            key = {
                it.games.gameId
            }
        ) { game ->
            GameItem(
                modifier = Modifier,
                game = game,
                onAthleteClicked = { athleteId ->
                    onAthleteClicked(athleteId)
                }
            )
        }
    }
}

@Composable
fun GameItem(
    modifier: Modifier,
    game: GameWithAthletes,
    onAthleteClicked: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(bottom = 20.dp)
    ) {
        Text(
            text = "${game.games.city} ${game.games.year}",
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
                .height(10.dp)
        )
        if (game.athletes.isEmpty()) {
            Text(
                text = "No athletes available for ${game.games.city} ${game.games.year}",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily.SansSerif,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        AthletesList(
            athletes = game.athletes,
            modifier = Modifier,
            onAthleteClicked = { athleteId ->
                onAthleteClicked(athleteId)
            }
        )
    }
}