package data.local.dao

import androidx.room.*
import data.local.entity.QuestEntity
import data.local.entity.QuestEntityWithActivationInfo
import data.local.entity.QuestRemoteUpdate
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {

    @Query("SELECT * FROM quest_table")
    suspend fun getAllQuests(): List<QuestEntity>

    @Query("SELECT * FROM quest_table")
    fun observeAllQuests(): Flow<List<QuestEntity>>

    @Query("SELECT * FROM quest_table")
    @Transaction
    fun observeAllQuestsWithActivationState(): Flow<List<QuestEntityWithActivationInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuests(quests: List<QuestEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuest(questEntity: QuestEntity)

    @Update(QuestEntity::class)
    suspend fun updateQuestFromRemote(questRemoteUpdate: QuestRemoteUpdate)

    @Update(QuestEntity::class)
    suspend fun updateQuestsFromRemote(questRemoteUpdate: List<QuestRemoteUpdate>)

    @Update
    suspend fun updateQuest(questEntity: QuestEntity)

    @Query("UPDATE quest_table SET wasCompletedByUser = TRUE WHERE id = :questId")
    suspend fun setQuestWithIdAsCompleted(questId: String)
}