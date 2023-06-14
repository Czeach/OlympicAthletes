package com.czech.olympicathletes.ui.screens.athlete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.olympicathletes.data.repository.AthletesRepository
import com.czech.olympicathletes.data.state.DataState
import com.czech.olympicathletes.network.models.GameWithAthletes
import com.czech.olympicathletes.ui.screens.states.AthleteListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AthletesListViewModel @Inject constructor(
    private val athletesRepository: AthletesRepository,
): ViewModel() {

    /**
     * collect data on initialisation of view model
     */
    init {
        getAthletes()
    }

    /**
     * refreshing state
     */
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing

    /**
     * state flow object that store data states
     */
    private val _athletesState = MutableStateFlow<AthleteListState>(AthleteListState.Loading)
    val athletesState: StateFlow<AthleteListState> = _athletesState

    /**
     * Refresh the athletes list
     */
    fun swipeDownToRefresh() {
        viewModelScope.launch {
            getAthletes()
            _isRefreshing.emit(false)
        }
    }

    /**
     * Retrieve athletes from the repository and emit them into the state flow
     */
    fun getAthletes() {
        viewModelScope.launch {
            athletesRepository.getGamesWithAthletes().collect { state ->
                _athletesState.emit(state.toAthleteListState())
            }
        }
    }

    /**
     * Extension function to convert DataState to AthleteListState
     */
    private fun DataState<List<GameWithAthletes>>.toAthleteListState(): AthleteListState {
        return when {
            isLoading -> AthleteListState.Loading
            isError -> AthleteListState.Error(message)
            else -> AthleteListState.Success(data)
        }
    }
}