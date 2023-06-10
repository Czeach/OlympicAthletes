package com.czech.olympicathletes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.czech.olympicathletes.R
import com.czech.olympicathletes.network.models.Athletes

@Composable
fun AthletesList(
    athletes: List<Athletes>
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()

    ) {
        items(
            items = athletes
        ) { athletes ->
            AthleteItem(
                modifier = Modifier
                    .padding(end = 4.dp),
                athleteName = "${athletes.name} ${athletes.surname}"
            )
        }
    }
}

@Composable
fun AthleteItem(
    modifier: Modifier,
    athleteName: String,
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .height(150.dp)
            .width(100.dp)
            .clickable(
                enabled = true,
                onClick = {
                }
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
        )
    ) {
        val constrainSet = ConstraintSet {
            val image = createRefFor("image")
            val name = createRefFor("name")

            constrain(image) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(name) {
                start.linkTo(image.start)
                end.linkTo(image.end)
                bottom.linkTo(image.bottom)
            }
        }

        ConstraintLayout(
            constraintSet = constrainSet,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                modifier = Modifier
                    .layoutId("image")
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "athlete image",
            )
            Text(
                text = athleteName,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier
                    .padding(bottom = 8.dp, start = 4.dp, end = 4.dp)
                    .layoutId("name")
            )
        }
    }
}