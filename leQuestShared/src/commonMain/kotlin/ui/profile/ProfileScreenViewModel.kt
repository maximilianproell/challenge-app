package ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileScreenViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val logger = Logger.withTag(this::class.simpleName!!)

    // TODO: Just placeholder data.
    private val _screenState = MutableStateFlow(
        ProfileScreenState(
            userName = "Max Mustermann",
            level = "Level 3",
        )
    )

    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.observeUser().collect { user ->
                _screenState.update {
                    it.copy(xp = user.xp)
                }
            }
        }
    }
}