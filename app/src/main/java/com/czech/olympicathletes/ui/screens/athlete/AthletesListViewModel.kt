package com.czech.olympicathletes.ui.screens.athlete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czech.olympicathletes.data.repository.AthleteRepository
import com.czech.olympicathletes.data.repository.GamesRepository
import com.czech.olympicathletes.ui.screens.states.AthleteListState
import com.czech.olympicathletes.ui.screens.states.GamesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AthletesListViewModel @Inject constructor(
    private val gamesRepository: GamesRepository,
    private val athleteRepository: AthleteRepository
): ViewModel() {

    init {
        getGames()
    }

    private val _gamesState = MutableStateFlow<GamesState?>(null)
    val gamesState: StateFlow<GamesState?> = _gamesState

    private val _athletesState = MutableStateFlow<AthleteListState?>(null)
    val athletesState: StateFlow<AthleteListState?> = _athletesState

    private fun getGames() {
        viewModelScope.launch {
            gamesRepository.getGames().collect {
                when {
                    it.isLoading -> {
                        _gamesState.value = GamesState.Loading
                    }
                    it.success -> {
                        it.data.let { games ->
                            _gamesState.value = GamesState.Success(data = games)
                        }
                    }
                    else -> {
                        _gamesState.value = GamesState.Error(message = it.message.toString())
                    }
                }
            }
        }
    }

    fun getAthletes(gameId: Int) {
        viewModelScope.launch {
            athleteRepository.getAthletes(gameId).collect {
                when {
                    it.isLoading -> {
                        _athletesState.value = AthleteListState.Loading
                    }
                    it.success -> {
                        it.data.let { athletes ->
                            _athletesState.value = AthleteListState.Success(data = athletes)
                        }
                    }
                    else -> {
                        _athletesState.value = AthleteListState.Error(message = it.message.toString())
                    }
                }
            }
        }
    }
}