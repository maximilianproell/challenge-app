package domain.repository

import domain.model.Quest
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {

    fun observeVisibleChallenges(): Flow<List<Quest>>

    suspend fun activateChallenge(quest: Quest, activationCode: String)

    suspend fun completeChallenge(challengeId: String, completionCode: String)
}