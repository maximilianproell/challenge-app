package data.repository

import domain.model.Challenge
import domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow

class ChallengeRepositoryImpl: ChallengeRepository {
    override fun observeVisibleChallenges(): Flow<List<Challenge>> {
        TODO("Not yet implemented")
    }
}