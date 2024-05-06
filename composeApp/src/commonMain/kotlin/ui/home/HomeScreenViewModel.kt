package ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.ChallengeRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    // TODO: inject repo.
    private val challengesRepository = ChallengeRepositoryImpl()

    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            challengesRepository.observeVisibleChallenges().collect { challenges ->
                _screenState.update {
                    it.copy(challenges = challenges)
                }
            }
        }
    }

    fun onChallengeAccepted() {
        _screenState.update {
            it.copy(isChallengeInProgress = true)
        }
    }

}