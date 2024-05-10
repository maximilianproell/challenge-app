package data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import data.local.entity.QuestEntity
import data.local.entity.QuestRemoteUpdate
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestDao {

    @Query("SELECT * FROM quest_table")
    suspend fun getAllQuests(): List<QuestEntity>

    @Query("SELECT * FROM quest_table")
    fun observeAllQuests(): Flow<List<QuestEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuests(quests: List<QuestEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuest(questEntity: QuestEntity)

    @Update(QuestEntity::class)
    suspend fun updateQuestFromRemote(questRemoteUpdate: QuestRemoteUpdate)

    @Update(QuestEntity::class)
    suspend fun updateQuestsFromRemote(questRemoteUpdate: List<QuestRemoteUpdate>)
}