package ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeScreenViewModel : ViewModel(), KoinComponent {

    private val challengesRepository: ChallengeRepository by inject()

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