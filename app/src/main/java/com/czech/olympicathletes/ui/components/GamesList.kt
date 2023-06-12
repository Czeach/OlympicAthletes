package com.czech.olympicathletes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czech.olympicathletes.network.models.GameWithAthletes

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
                it.game.gameId
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
            text = "${game.game.city} ${game.game.year}",
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
                text = "No athletes available for ${game.game.city} ${game.game.year}",
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