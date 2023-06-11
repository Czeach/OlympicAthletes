package com.czech.olympicathletes.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.czech.olympicathletes.R
import com.czech.olympicathletes.network.models.AthleteInfo
import com.czech.olympicathletes.network.models.AthleteInfoWithResults
import com.czech.olympicathletes.network.models.AthleteResults
import com.czech.olympicathletes.utils.Constants
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.*
import com.halilibo.richtext.ui.material.MaterialRichText
import com.halilibo.richtext.ui.string.RichTextStringStyle
import com.mikepenz.markdown.Markdown
import com.mikepenz.markdown.MarkdownDefaults

@Composable
fun AthleteDetails(
    modifier: Modifier,
    athleteDetails: AthleteInfoWithResults
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(start = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(140.dp)
                    .height(150.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                )
            ) {
                val imageUrl = "${Constants.BASE_URL}athletes/${athleteDetails.athleteInfo.athleteId}/photo"
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "athlete image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                )
            }
            Spacer(
                modifier = Modifier
                    .width(12.dp)
            )
            AthleteInfo(
                modifier = Modifier,
                athleteInfo = athleteDetails.athleteInfo,
            )
        }
        Text(
            text = "Medals",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            fontFamily = FontFamily.SansSerif,
            maxLines = 1,
            modifier = Modifier
                .padding(4.dp)
        )
        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            items(
                items = athleteDetails.athleteResults
            ) { result ->
                MedalsItem(
                    athleteResults = result,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                )
            }
        }
        Text(
            text = "Bio",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            fontFamily = FontFamily.SansSerif,
            maxLines = 1,
            modifier = Modifier
                .padding(4.dp)
        )
        Markdown(
            content = """
                    ${athleteDetails.athleteInfo.bio}
                """.trimIndent(),
            colors = MarkdownDefaults.markdownColors(
                textColor = MaterialTheme.colorScheme.secondary
            ),
            typography = MarkdownDefaults.markdownTypography(
                h3 = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily.SansSerif
                ),
                h4 = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily.SansSerif
                ),
                body1 = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif
                )
            ),
            modifier = Modifier
                .padding(bottom = 12.dp)
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun AthleteInfo(
    modifier: Modifier,
    athleteInfo: AthleteInfo
) {
    Column {
        Spacer(
            modifier = modifier
                .height(14.dp)
        )
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = FontFamily.SansSerif,
                    )
                ) {
                    append("Name: ")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily.SansSerif,
                    )
                ) {
                    append("${athleteInfo.name} ${athleteInfo.surname}")
                }
            },
            modifier = modifier
        )
        Spacer(
            modifier = modifier
                .height(12.dp)
        )
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = FontFamily.SansSerif,
                    )
                ) {
                    append("BOD: ")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily.SansSerif,
                    )
                ) {
                    append(athleteInfo.dateOfBirth.toString())
                }
            },
            modifier = modifier
        )
        Spacer(
            modifier = modifier
                .height(12.dp)
        )
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = FontFamily.SansSerif,
                    )
                ) {
                    append("Weight: ")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily.SansSerif,
                    )
                ) {
                    append("${athleteInfo.weight}kg")
                }
            },
            modifier = modifier
        )
        Spacer(
            modifier = modifier
                .height(12.dp)
        )
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = FontFamily.SansSerif,
                    )
                ) {
                    append("Height: ")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily.SansSerif,
                    )
                ) {
                    append("${athleteInfo.height}cm")
                }
            },
            modifier = modifier
        )
    }
}

@Composable
fun MedalsItem(
    modifier: Modifier,
    athleteResults: AthleteResults
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "${athleteResults.city} ${athleteResults.year}",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            fontFamily = FontFamily.SansSerif,
            maxLines = 1,
            modifier = modifier
                .padding(start = 18.dp, end = 24.dp)
        )
        if (athleteResults.gold!! > 0) {
            Text(
                text = athleteResults.gold.toString(),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily.SansSerif,
                maxLines = 1,
                modifier = modifier
            )
            Image(
                painter = painterResource(id = R.drawable.gold_medal),
                contentDescription = "gold medal",
                modifier = modifier
                    .height(20.dp)
                    .width(20.dp)
                    .padding(end = 4.dp)
            )
        }
        if (athleteResults.silver!! > 0) {
            Text(
                text = athleteResults.silver.toString(),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily.SansSerif,
                maxLines = 1,
                modifier = modifier
            )
            Image(
                painter = painterResource(id = R.drawable.silver_medal),
                contentDescription = "silver medal",
                modifier = modifier
                    .height(20.dp)
                    .width(20.dp)
                    .padding(end = 4.dp)
            )
        }
        if (athleteResults.bronze!! > 0) {
            Text(
                text = athleteResults.bronze.toString(),
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily.SansSerif,
                maxLines = 1,
                modifier = modifier
            )
            Image(
                painter = painterResource(id = R.drawable.bronze_medal),
                contentDescription = "bronze medal",
                modifier = modifier
                    .height(20.dp)
                    .width(20.dp)
            )
        }

    }
}