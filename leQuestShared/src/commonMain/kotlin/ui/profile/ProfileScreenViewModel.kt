package ui.profile

import androidx.lifecycle.ViewModel
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileScreenViewModel : ViewModel() {

    private val logger = Logger.withTag(this::class.simpleName!!)

    // TODO: Just placeholder data.
    private val _screenState = MutableStateFlow(
        ProfileScreenState(
            userName = "Max Mustermann",
            level = "Level 3",
        )
    )

    val screenState = _screenState.asStateFlow()
}