package ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import domain.model.Quest
import domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
            challengesRepository.observeVisibleChallenges()
                .catch {
                    // TODO: display error on screen
                    logger.e { "Error occurred observing the quests: ${it.stackTraceToString()}" }
                }
                .collect { challenges ->
                    _screenState.update {
                        it.copy(quests = challenges)
                    }
                }
        }
    }

    fun onQuestAccepted(quest: Quest) {
        // TODO: update quest in data layer (set it to currently active)
        _screenState.update {
            it.copy(
                selectedQuest = quest.copy(
                    isCurrentlyActive = true,
                )
            )
        }
    }

    fun onQuestSelected(quest: Quest) {
        _screenState.update {
            it.copy(
                selectedQuest = quest
            )
        }
    }
}