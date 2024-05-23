package ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import domain.model.Quest
import domain.repository.QuestsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import lequestapp.lequestshared.generated.resources.Res
import lequestapp.lequestshared.generated.resources.home_error_activation_data_does_not_match
import lequestapp.lequestshared.generated.resources.home_error_activation_data_invalid
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeScreenViewModel : ViewModel(), KoinComponent {

    private val questsRepository: QuestsRepository by inject()

    private val _screenState = MutableStateFlow(HomeScreenState())
    val screenState = _screenState.asStateFlow()

    private val logger = Logger.withTag("HomeScreenViewModel")

    init {
        viewModelScope.launch {
            launch {
                try {
                    questsRepository.updateQuestsFromRemote()
                } catch (exception: Exception) {
                    if (exception is CancellationException) throw exception
                    logger.e { "Error occurred updating the quests from remote: ${exception.stackTraceToString()}" }
                    // else TODO: Show some kind of network error so the user knows that data may be stale.
                }
            }

            questsRepository.observeVisibleQuests()
                .collect { quests ->
                    _screenState.update {
                        it.copy(quests = quests)
                    }

                    // Update selected quest, if present. If there is no quest found from the data source, it will
                    // be unselected.
                    val updatedSelectedQuest = quests.find { it.id == _screenState.value.selectedQuest?.id }
                    onQuestSelected(updatedSelectedQuest)

                    // If there is a currently active quest, we set it here as selected, since no other quest can
                    // be selected anyways.
                    quests.find { it.activationInfo != null }?.let { activeQuest ->
                        onQuestSelected(activeQuest)
                    }
                }
        }
    }

    fun onQuestAccepted(quest: Quest) {
        viewModelScope.launch {
            // TODO: activation code should be set?
            questsRepository.activateQuest(quest = quest)
        }
    }

    fun onQuestSelected(quest: Quest?) {
        _screenState.update {
            it.copy(
                selectedQuest = quest
            )
        }
    }

    /**
     * Handles the activation or completion of a quest.
     * @param qrCodeData The data scanned from the QR code.
     */
    fun onQrCodeScanned(qrCodeData: String) {
        val quest = _screenState.value.selectedQuest

        if (quest != null) {
            // Completion data is expected.
            if (qrCodeData.startsWith("c")) {
                // Valid data.
                if (qrCodeData == quest.completionData) {
                    viewModelScope.launch {
                        questsRepository.completeQuest(questId = quest.id)
                    }
                } else {
                    _screenState.update {
                        it.copy(
                            errorStringResource = Res.string.home_error_activation_data_does_not_match
                        )
                    }
                }
            } else {
                _screenState.update {
                    it.copy(
                        errorStringResource = Res.string.home_error_activation_data_invalid
                    )
                }
            }
        } else {
            // Activation data is expected.
            if (qrCodeData.startsWith("a")) {
                // Valid data.
                // TODO: Add logic to handle activation of quests.
            } else {
                _screenState.update {
                    it.copy(
                        errorStringResource = Res.string.home_error_activation_data_invalid
                    )
                }
            }
        }
    }

    fun onErrorMessageDismissed() {
        _screenState.update {
            it.copy(
                errorStringResource = null
            )
        }
    }
}