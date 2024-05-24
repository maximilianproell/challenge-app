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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class QuestRepositoryImpl(
    private val questRemoteDataSource: QuestRemoteDataSource,
    private val questLocalDataSource: QuestLocalDataSource
) : QuestsRepository {

    private val logger = Logger.withTag(this::class.simpleName!!)

    private val pingFlow = flow {
        while (true) {
            emit(Unit)
            delay(15.seconds)
        }
    }

    override fun observeVisibleQuests(): Flow<List<Quest>> =
        pingFlow.combine(questLocalDataSource.observeAllQuestsWithActivationState()) { _, list -> list }.map { list ->
            logger.d { "Got quests with activation info from DB: $list" }
            val isAtLeastOneQuestActive = list.any { it.activeInfo != null }
            // Filter out completed and non-visible quests.
            list.filter { it.questEntity.isVisibleOnMap && !it.questEntity.wasCompletedByUser }
                .map {
                    it.toDomain(
                        isAtLeastOneQuestActive = isAtLeastOneQuestActive,
                        onTimeRanOut = {
                            questLocalDataSource.setQuestToFailed(
                                activeQuest = ActiveQuestEntity(questId = it.questEntity.id, startTimestamp = 0)
                            )
                        }
                    )
                }
        }.distinctUntilChanged()

    override suspend fun getAllVisibleQuests(): List<Quest> {
        return questLocalDataSource.getAllQuestsWithActivationInfo()
            .filter { it.questEntity.isVisibleOnMap && !it.questEntity.wasCompletedByUser }
            .map { questEntityWithActivationInfo ->
                questEntityWithActivationInfo.toDomain(isAtLeastOneQuestActive = false, onTimeRanOut = {})
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

    override suspend fun activateQuest(quest: Quest) {
        questLocalDataSource.setQuestToActive(
            ActiveQuestEntity(
                questId = quest.id,
                startTimestamp = Clock.System.now().toEpochMilliseconds(),
            )
        )
    }

    override suspend fun completeQuest(questId: String) {
        questLocalDataSource.setQuestAsCompleted(questId)
        questLocalDataSource.setQuestToInactive(
            ActiveQuestEntity(questId = questId, startTimestamp = 0)
        )
    }
}