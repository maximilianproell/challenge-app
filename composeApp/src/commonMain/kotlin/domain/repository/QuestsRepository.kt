package domain.repository

import domain.model.Quest
import kotlinx.coroutines.flow.Flow

interface QuestsRepository {

    fun observeVisibleQuests(): Flow<List<Quest>>

    suspend fun activateQuest(quest: Quest, activationCode: String)

    suspend fun completeQuest(questId: String, completionCode: String)
}