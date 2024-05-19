package data.repository

import co.touchlab.kermit.Logger
import data.local.QuestLocalDataSource
import data.local.entity.ActiveQuestEntity
import data.mapper.toDbUpdate
import data.mapper.toDomain
import data.mapper.toEntity
import data.remote.QuestRemoteDataSource
import domain.model.Quest
import domain.model.QuestActivationInfo
import domain.repository.QuestsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class QuestRepositoryImpl(
    private val questRemoteDataSource: QuestRemoteDataSource,
    private val questLocalDataSource: QuestLocalDataSource
) : QuestsRepository {

    private val logger = Logger.withTag(this::class.simpleName!!)

    override fun observeVisibleQuests(): Flow<List<Quest>> =
        questLocalDataSource.observeAllQuestsWithActivationState().map { list ->
            logger.d { "Got quests with activation info from DB: $list" }
            val isAtLeastOneQuestActive = list.any { it.activeInfo != null }
            list.map {
                it.questEntity.toDomain(
                    questActivationInfo = it.activeInfo?.let { activeInfo ->
                        QuestActivationInfo(activationTimeStampMilliseconds = activeInfo.startTimestamp)
                    },
                    // Only clickable, if no other quest is active.
                    isClickable = !isAtLeastOneQuestActive
                )
            }
        }

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
        questLocalDataSource.setQuestToActive(
            ActiveQuestEntity(
                questId = quest.id,
                startTimestamp = Clock.System.now().toEpochMilliseconds(),
            )
        )
    }

    override suspend fun completeQuest(questId: String, completionCode: String) {
        // TODO: implement
    }
}