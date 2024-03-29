package ui.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.repository.ChallengeRepositoryImpl
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenModel : StateScreenModel<HomeScreenState>(HomeScreenState()) {

    // TODO: inject repo.
    val challengesRepository = ChallengeRepositoryImpl()

    init {
        screenModelScope.launch {
            challengesRepository.observeVisibleChallenges().collect { challenges ->
                mutableState.update {
                    it.copy(challenges = challenges)
                }
            }
        }
    }

    fun onChallengeAccepted() {
        mutableState.update {
            it.copy(isChallengeInProgress = true)
        }
    }
}