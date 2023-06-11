package com.czech.olympicathletes.ui.screens.states

import com.czech.olympicathletes.network.models.AthleteInfoWithResults

sealed interface AthleteDetailsState {
    data class Success(val data: AthleteInfoWithResults?): AthleteDetailsState
    data class Error(val message: String?): AthleteDetailsState
    object Loading: AthleteDetailsState
}