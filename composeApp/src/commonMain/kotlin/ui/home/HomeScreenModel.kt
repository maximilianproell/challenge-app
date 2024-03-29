package ui.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import co.touchlab.kermit.Logger
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class HomeScreenModel : StateScreenModel<HomeScreenState>(HomeScreenState()) {

    fun onChallengeAccepted() {
        mutableState.update {
            it.copy(isChallengeInProgress = true)
        }
    }
}