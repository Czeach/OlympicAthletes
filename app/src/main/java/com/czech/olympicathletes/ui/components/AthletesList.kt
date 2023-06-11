package com.czech.olympicathletes.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.utils.Constants

@Composable
fun AthletesList(
    athletes: List<Athletes>,
    onAthleteClicked: (String) -> Unit,
    modifier: Modifier
) {
    LazyRow(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(
            items = athletes,
            key = {
                it.athleteId
            }
        ) { athlete ->
            AthleteItem(
                modifier = modifier
                    .padding(end = 10.dp),
                athlete = athlete,
                onAthleteClicked = {
                    onAthleteClicked(athlete.athleteId)
                }
            )
        }
    }
}

@Composable
fun AthleteItem(
    modifier: Modifier,
    athlete: Athletes,
    onAthleteClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(100.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
            )
        ) {
            val imageUrl = "${Constants.BASE_URL}athletes/${athlete.athleteId}/photo"
            AsyncImage(
                model = imageUrl,
                contentDescription = "athlete image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onAthleteClicked()
                    }
            )
        }
        Text(
            text = "${athlete.name} ${athlete.surname}",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 13.sp,
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily.SansSerif,
            maxLines = 1,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        )
    }
}