package data.repository

import domain.repository.QuestsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RepositoryHelper : KoinComponent {
    val questsRepository: QuestsRepository by inject()
}