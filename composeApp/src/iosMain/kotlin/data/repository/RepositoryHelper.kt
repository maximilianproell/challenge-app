package data.repository

import domain.repository.ChallengeRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RepositoryHelper : KoinComponent {
    val challengeRepository: ChallengeRepository by inject()
}