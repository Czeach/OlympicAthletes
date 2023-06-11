package com.czech.olympicathletes.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.olympicathletes.data.repository.AthleteDetailsRepository
import com.czech.olympicathletes.ui.screens.states.AthleteDetailsState
import com.czech.olympicathletes.ui.screens.states.AthleteListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AthleteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val athleteDetailsRepository: AthleteDetailsRepository
): ViewModel() {

    private val athleteId = savedStateHandle.get<String>("athleteId")

    init {
        getAthleteDetails()
    }

    private val _detailsState = MutableStateFlow<AthleteDetailsState>(AthleteDetailsState.Loading)
    val detailsState: StateFlow<AthleteDetailsState> = _detailsState

    fun getAthleteDetails() {
        viewModelScope.launch {
            athleteDetailsRepository.getAthleteInfoWithResults(
                athleteId = athleteId!!
            ).collect { state ->
                if (state.isLoading) {
                    _detailsState.update {
                        AthleteDetailsState.Loading
                    }
                }
                if (state.isSuccess) {
                    _detailsState.update {
                        AthleteDetailsState.Success(
                            data = state.data
                        )
                    }
                }
                if (state.isError) {
                    _detailsState.update {
                        AthleteDetailsState.Error(
                            message = state.message
                        )
                    }
                }
            }
        }
    }
}