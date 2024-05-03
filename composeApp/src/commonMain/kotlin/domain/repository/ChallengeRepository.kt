package domain.repository

import domain.model.Challenge
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {

    fun observeVisibleChallenges(): Flow<List<Challenge>>

    suspend fun activateChallenge(challenge: Challenge, activationCode: String)

    suspend fun completeChallenge(challengeId: String, completionCode: String)
}