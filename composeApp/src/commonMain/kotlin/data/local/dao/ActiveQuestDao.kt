package data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import data.local.entity.ActiveQuestEntity

@Dao
interface ActiveQuestDao {

    @Query("SELECT * FROM active_quest_table LIMIT 1")
    suspend fun getActiveQuestData(): ActiveQuestEntity?

    @Insert
    suspend fun insertActiveQuestData(activeQuestEntity: ActiveQuestEntity)

    @Delete
    suspend fun deleteActiveQuestData(activeQuestEntity: ActiveQuestEntity)
}