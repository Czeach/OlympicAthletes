package com.czech.olympicathletes.ui.screens.athlete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.olympicathletes.data.repository.AthletesRepository
import com.czech.olympicathletes.ui.screens.states.AthleteListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AthletesListViewModel @Inject constructor(
    private val athletesRepository: AthletesRepository,
): ViewModel() {

    init {
        getAthletes()
    }

    private val _athletesState = MutableStateFlow<AthleteListState>(AthleteListState.Loading)
    val athletesState: StateFlow<AthleteListState> = _athletesState

    fun getAthletes() {
        viewModelScope.launch {
            athletesRepository.getGamesWithAthletes().collect { state ->
                if (state.isLoading) {
                    _athletesState.update {
                        AthleteListState.Loading
                    }
                }
                if (state.isSuccess) {
                    _athletesState.update {
                        AthleteListState.Success(
                            data = state.data
                        )
                    }
                }
                if (state.isError) {
                    _athletesState.update {
                        AthleteListState.Error(
                            message = state.message
                        )
                    }
                }
            }
        }
    }
}