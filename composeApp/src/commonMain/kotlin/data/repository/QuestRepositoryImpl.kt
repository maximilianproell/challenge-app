package data.repository

import data.local.QuestLocalDataSource
import data.mapper.toDbUpdate
import data.mapper.toDomain
import data.mapper.toEntity
import data.remote.QuestRemoteDataSource
import domain.model.Quest
import domain.repository.QuestsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class QuestRepositoryImpl(
    private val questRemoteDataSource: QuestRemoteDataSource,
    private val questLocalDataSource: QuestLocalDataSource
) : QuestsRepository {

    override fun observeVisibleQuests(): Flow<List<Quest>> =
        questLocalDataSource.observeAllQuests().map { list -> list.map { it.toDomain() } }

    override suspend fun updateQuestsFromRemote() {
        val remoteQuests = questRemoteDataSource.getAllQuests()
        // Insert all quests from remote into DB.
        questLocalDataSource.insert(remoteQuests.map { it.toEntity() })
        // Update existing elements (since insert ignores elements with primary key collisions)
        questLocalDataSource.updateQuestsFromRemote(
            remoteQuests.map { it.toDbUpdate() }
        )
    }

    override suspend fun activateQuest(quest: Quest, activationCode: String) {
        questLocalDataSource.setQuestToActive(questId = quest.id)
    }

    override suspend fun completeQuest(questId: String, completionCode: String) {
        // TODO: implement
    }
}