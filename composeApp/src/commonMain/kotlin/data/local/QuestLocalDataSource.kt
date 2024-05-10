package data.local

import data.local.dao.QuestDao
import data.local.entity.QuestEntity
import data.local.entity.QuestRemoteUpdate
import kotlinx.coroutines.flow.Flow

interface QuestLocalDataSource {
    suspend fun insert(quests: List<QuestEntity>)
    suspend fun getAllQuests(): List<QuestEntity>
    fun observeAllQuests(): Flow<List<QuestEntity>>
    suspend fun updateQuestsFromRemote(updates: List<QuestRemoteUpdate>)
    suspend fun updateQuestFromRemote(update: QuestRemoteUpdate)
}

class QuestLocalDataSourceImpl(
    private val questDao: QuestDao,
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

    override suspend fun updateQuestsFromRemote(updates: List<QuestRemoteUpdate>) {
        return questDao.updateQuestsFromRemote(updates)
    }

    override suspend fun updateQuestFromRemote(update: QuestRemoteUpdate) {
        questDao.updateQuestFromRemote(update)
    }
}