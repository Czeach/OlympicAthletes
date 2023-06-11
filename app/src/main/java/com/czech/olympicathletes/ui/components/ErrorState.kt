package com.czech.olympicathletes.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorState(
    message: String,
    btnText: String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 15.sp,
                fontWeight = FontWeight.W400,
                fontFamily = FontFamily.SansSerif,
            )
            Spacer(
                modifier = modifier
                    .height(10.dp)
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                shape = RoundedCornerShape(6.dp),
                onClick = { onClick() }
            ) {
                Text(
                    text = btnText,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                )
            }
        }
    }
}