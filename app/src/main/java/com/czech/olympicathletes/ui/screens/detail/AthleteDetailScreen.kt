package com.czech.olympicathletes.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.network.models.AthleteResults
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
    viewModel: AthleteDetailViewModel
) {
    val detailsState by viewModel.detailsState.collectAsState()

    Content(
        state = detailsState,
        onBackPressed = { onBackPressed() },
        refresh = { viewModel.getAthleteDetails() }

    )
}

@Composable
private fun Content(
    state: AthleteDetailsState,
    onBackPressed: () -> Unit,
    refresh: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val title = remember {
        mutableStateOf("")
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 6.dp, bottom = 6.dp)
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onBackPressed() },
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "back_button"
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

        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            when (state) {
                is AthleteDetailsState.Loading -> {
                    LoadingState(
                        text = "Fetching Details...",
                        modifier = Modifier
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
                        )
                    }
                }
                is AthleteDetailsState.Error -> {
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
fun AthleteDetailScreenPreview() {
    OlympicAthletesTheme {
        Content(
            state = AthleteDetailsState.Success(
                data = AthleteWithResults(
                    athlete = Athletes(
                        UUID.randomUUID().toString(),
                        name = "Name",
                        surname = "Surname",
                        dateOfBirth = "14/04/1990",
                        bio = "Arianna Fontana OMRI (born April 14, 1990) is an Italian short track speed skater, who has won eight Olympic medals, among these a gold in the 500m short track at the 2018 Winter Olympics in Pyeongchang. Her medal haul following the 2018 Games made her the female short track skater with the most Olympic medals, and tied her with male skaters Apolo Ohno and Viktor An. It also made her the Italian sportswoman with the second highest number of Winter Olympic medals, behind Stefania Belmondo. She specialises in the 500 m event. She is nicknamed La Freccia Bionda (The Blonde Arrow).\\n\\nBiography\\n=======\\nFontana started skating at the age of four, following in the footsteps of her brother Alessandro, initially on roller skates before switching to ice skating. She first trained in Lanzada until the rink there closed, when she switched her training base to Bormio.\\n\\nFontana made her international championship debut at the 2006 European Short Track Speed Skating Championships in Krynica-Zdrój, where she took the overall silver medal. At the 2006 Winter Olympics in Turin, she won bronze in the 3000 m relay. She placed 11th in the 500m and 6th in the 1000 m. The relay medal was the first for Italy in short track speed skating: at 15 years of age, Fontana became the youngest Italian to win a Winter Olympic medal. Following the 2006 Games, Fontana and her relay team-mates were appointed Knights of the Order of Merit of the Italian Republic.\\n\\nAt the 2010 Winter Olympics in Vancouver, she won a bronze medal in the 500 metre event, was eliminated in the semifinals of the 1500 metre event, and was eliminated in the quarterfinals of the 1000 metre event. Her 500m bronze made her the first Italian to take an individual Olympic medal in short track.\\n\\nFontano began dating Italian-American skater Anthony Lobello Jr. in 2012: the couple were engaged the following year and married in May 2014 in Colico. The couple split their time between homes in Valtellina, Courmayeur and Tallahassee, Florida.\\n\\nAt the 2014 Winter Olympics in Sochi, she was upgraded from a bronze to a silver medal in the 500 m event after colliding with British skater Elise Christie. Christie was disqualified after causing a crash in the final. She won a bronze medal in the 1500 m event and in the Team-event, and was disqualified in the 1000 m event. Following the Games, Lobello began coaching Fontana following his retirement from competition. The following year Fontana took her first title at the World Short Track Speed Skating Championships, winning the gold in the 1500m and taking the overall silver.\\n\\nIn October 2017, Fontana was named as Italy's flag bearer for the opening ceremony of the 2018 Winter Olympics in PyeongChang, South Korea. She was the first short track skater to be selected as flag bearer for the Italian Olympic team and the second flag bearer from the Italian Ice Sports Federation, after Carolina Kostner. She won her first Olympic gold medal there, in the 500 m event.She was the first European to win a 500 m Olympic gold. She also won silver in the team event and bronze in the 1000 m event, becoming the woman with most medals in the sport. It also meant that she had won Olympic medals at every contested distance.",
                        weight = 56,
                        height = 174,
                        photoId = Random.nextInt(),
                    ),
                    athleteResults = (0..5).map {
                        AthleteResults(
                            city = "Tokyo",
                            year = 2023,
                            gold = 5,
                            silver = 3,
                            bronze = 1
                        )
                    }
                )
            ),
            onBackPressed = {},
            refresh = {}
        )
    }
}