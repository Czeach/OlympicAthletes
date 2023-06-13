package com.czech.olympicathletes.ui.screens.athlete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.olympicathletes.data.repository.AthletesRepository
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

    init {
        getAthletes()
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing

    private val _athletesState = MutableStateFlow<AthleteListState>(AthleteListState.Loading)
    val athletesState: StateFlow<AthleteListState> = _athletesState

    fun swipeDownToRefresh() {
        viewModelScope.launch {
            getAthletes()
            _isRefreshing.emit(false)
        }
    }

    fun getAthletes() {
        viewModelScope.launch {
            athletesRepository.getGamesWithAthletes().collect { state ->
                if (state.isLoading) {
                    _athletesState.emit(
                        AthleteListState.Loading
                    )
                }
                if (state.isSuccess) {
                    _athletesState.emit(
                        AthleteListState.Success(
                            data = state.data
                        )
                    )
                }
                if (state.isError) {
                    _athletesState.emit(
                        AthleteListState.Error(
                            message = state.message
                        )
                    )
                }
            }
        }
    }
}