package com.czech.olympicathletes.ui.screens.athlete

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czech.olympicathletes.ui.components.GamesList
import com.czech.olympicathletes.ui.screens.states.GamesState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AthletesListScreen(
    viewModel: AthletesListViewModel
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
            )
        }
    ) {
        
        ObserveGames(viewModel = viewModel)

    }
}

@Composable
fun ObserveGames(
    viewModel: AthletesListViewModel
) {
    when (val state = viewModel.gamesState.collectAsState().value) {
        is GamesState.Loading -> {

        }
        is GamesState.Success -> {
            val games = state.data

            if (games != null) {
                GamesList(
                    games = games,
                    viewModel = viewModel,
                    listState = rememberLazyListState(),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        is GamesState.Error -> {

        }
        null -> {

        }
    }
}

//@Composable
//fun ObserveAthletes(
//    state: AthleteListState?,
//    games: List<Games>
//) {
//    when (state) {
//        is AthleteListState.Loading -> {
//
//        }
//        is AthleteListState.Success -> {
//            val athletes = state.data
//
//            if (athletes != null) {
//                GamesList(
//                    games = games,
//                    listState = rememberLazyListState(),
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//        }
//        is AthleteListState.Error -> {
//
//        }
//        null -> {
//
//        }
//    }
//}