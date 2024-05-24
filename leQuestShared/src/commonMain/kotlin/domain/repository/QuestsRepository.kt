package domain.repository

import domain.model.Quest
import kotlinx.coroutines.flow.Flow

interface QuestsRepository {

    fun observeVisibleQuests(): Flow<List<Quest>>

    /**
     * Gets all visible quests from the local data source.
     */
    suspend fun getAllVisibleQuests(): List<Quest>

    /**
     * Fetches the Quests from the remote data source and updates the local database.
     */
    suspend fun updateQuestsFromRemote()

    suspend fun activateQuest(quest: Quest)

    suspend fun completeQuest(questId: String)
}