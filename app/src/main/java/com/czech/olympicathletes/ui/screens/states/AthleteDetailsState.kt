package com.czech.olympicathletes.ui.screens.states

import com.czech.olympicathletes.network.models.AthleteWithResults

sealed interface AthleteDetailsState {
    data class Success(val data: AthleteWithResults?): AthleteDetailsState
    data class Error(val message: String?): AthleteDetailsState
    object Loading: AthleteDetailsState
}