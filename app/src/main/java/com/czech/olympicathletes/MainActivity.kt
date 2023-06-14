package com.czech.olympicathletes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.czech.olympicathletes.ui.navigation.AppNavHost
import com.czech.olympicathletes.ui.theme.OlympicAthletesTheme
import dagger.hilt.android.AndroidEntryPoint


/**
 * app entry point. Launches app with base activity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OlympicAthletesApp()
        }
    }
}

@Composable
fun OlympicAthletesApp() {
    OlympicAthletesTheme {
        val navController = rememberNavController()
        AppNavHost(
            navController = navController
        )
    }
}