package ui.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update

class HomeScreenModel : StateScreenModel<HomeScreenState>(HomeScreenState()) {
    fun onChallengeAccepted() {
        mutableState.update {
            it.copy(isChallengeInProgress = true)
        }
    }
}