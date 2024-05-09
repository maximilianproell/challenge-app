package ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
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

    private val logger = Logger.withTag("HomeScreenViewModel")

    init {
        viewModelScope.launch {
            challengesRepository.observeVisibleChallenges().collect { challenges ->
                _screenState.update {
                    it.copy(quests = challenges)
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