package com.czech.olympicathletes.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.olympicathletes.data.repository.AthleteDetailsRepository
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.AthleteWithResults
import com.czech.olympicathletes.ui.screens.states.AthleteDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AthleteDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val athleteDetailsRepository: AthleteDetailsRepository
): ViewModel() {

    /**
     * Retrieve the selected athlete ID from the SavedStateHandle
     */
    private val athleteId = savedStateHandle.get<String>("athleteId")

    /**
     * collect data on initialisation of view model
     */
    init {
        getAthleteDetails()
    }

    /**
     * refresh state
     */
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing

    /**
     * state flow object that store data states
     */
    private val _detailsState = MutableStateFlow<AthleteDetailsState>(AthleteDetailsState.Loading)
    val detailsState: StateFlow<AthleteDetailsState> = _detailsState

    /**
     * Refresh the athlete details
     */
    fun swipeDownToRefresh() {
        viewModelScope.launch {
            getAthleteDetails()
            _isRefreshing.emit(false)
        }
    }

    /**
     * Retrieve athlete details from the repository and emit them into the state flow
     */
    fun getAthleteDetails() {
        viewModelScope.launch {
            athleteDetailsRepository.getAthleteWithResults(
                athleteId = athleteId!!
            ).collect { state ->
                _detailsState.emit(state.toAthleteDetailsState())
            }
        }
    }

    /**
     * Extension function to convert DataState to AthleteDetailsState
     */
    private fun DataState<AthleteWithResults>.toAthleteDetailsState(): AthleteDetailsState {
        return when {
            isLoading -> AthleteDetailsState.Loading
            isError -> AthleteDetailsState.Error(message)
            else -> AthleteDetailsState.Success(data)
        }
    }
}