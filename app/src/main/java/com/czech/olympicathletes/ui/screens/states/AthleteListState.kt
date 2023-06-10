package com.czech.olympicathletes.ui.screens.states

import com.czech.olympicathletes.network.models.Athletes
import com.czech.olympicathletes.network.models.Games

sealed interface AthleteListState {
    data class Success(val data: List<Athletes>?) : AthleteListState
    data class Error(val message: String?) : AthleteListState
    object Loading : AthleteListState
}