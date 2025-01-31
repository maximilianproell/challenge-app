package data.local

import data.local.dao.ActiveQuestDao
import data.local.dao.QuestDao
import data.local.entity.ActiveQuestEntity
import data.local.entity.QuestEntity
import data.local.entity.QuestEntityWithActivationInfo
import data.local.entity.QuestRemoteUpdate
import kotlinx.coroutines.flow.Flow

interface QuestLocalDataSource {
    suspend fun insert(quests: List<QuestEntity>)
    suspend fun getAllQuests(): List<QuestEntity>
    fun observeAllQuests(): Flow<List<QuestEntity>>
    fun observeAllQuestsWithActivationState(): Flow<List<QuestEntityWithActivationInfo>>
    suspend fun getAllQuestsWithActivationInfo(): List<QuestEntityWithActivationInfo>
    suspend fun updateQuestsFromRemote(updates: List<QuestRemoteUpdate>)
    suspend fun updateQuestFromRemote(update: QuestRemoteUpdate)
    suspend fun setQuestAsCompleted(questId: String)
    suspend fun setQuestToActive(activeQuest: ActiveQuestEntity)
    suspend fun setQuestToInactive(activeQuest: ActiveQuestEntity)
    suspend fun setQuestToFailed(activeQuest: ActiveQuestEntity)
}

class QuestLocalDataSourceImpl(
    private val questDao: QuestDao,
    private val activeQuestDao: ActiveQuestDao,
) : QuestLocalDataSource {
    override suspend fun insert(quests: List<QuestEntity>) {
        questDao.insertQuests(quests)
    }

    override suspend fun getAllQuests(): List<QuestEntity> {
        return questDao.getAllQuests()
    }

    override fun observeAllQuests(): Flow<List<QuestEntity>> {
        return questDao.observeAllQuests()
    }

    override fun observeAllQuestsWithActivationState(): Flow<List<QuestEntityWithActivationInfo>> {
        return questDao.observeAllQuestsWithActivationState()
    }

    override suspend fun getAllQuestsWithActivationInfo(): List<QuestEntityWithActivationInfo> {
        return questDao.getAllQuestsWithActivationInfo()
    }

    override suspend fun updateQuestsFromRemote(updates: List<QuestRemoteUpdate>) {
        return questDao.updateQuestsFromRemote(updates)
    }

    override suspend fun updateQuestFromRemote(update: QuestRemoteUpdate) {
        questDao.updateQuestFromRemote(update)
    }

    override suspend fun setQuestAsCompleted(questId: String) {
        questDao.setQuestWithIdAsCompleted(questId)
    }

    override suspend fun setQuestToActive(activeQuest: ActiveQuestEntity) {
        activeQuestDao.insertActiveQuestData(activeQuest)
    }

    override suspend fun setQuestToInactive(activeQuest: ActiveQuestEntity) {
        activeQuestDao.deleteActiveQuestData(activeQuest)
    }

    override suspend fun setQuestToFailed(activeQuest: ActiveQuestEntity) {
        // TODO: Needs to be discussed, if "failed" is actually a state we want to have.
        activeQuestDao.deleteActiveQuestData(activeQuest)
    }
}