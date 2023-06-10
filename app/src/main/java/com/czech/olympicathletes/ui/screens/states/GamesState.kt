package com.czech.olympicathletes.ui.screens.states

import com.czech.olympicathletes.network.models.Games

sealed interface GamesState {
    data class Success(val data: List<Games>?) : GamesState
    data class Error(val message: String?) : GamesState
    object Loading : GamesState
}