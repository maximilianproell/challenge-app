package data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "active_quest_table",
    foreignKeys = [
        ForeignKey(
            entity = QuestEntity::class,
            parentColumns = ["id"],
            childColumns = ["questId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ActiveQuestEntity(
    @PrimaryKey
    val questId: String,
    val startTimestamp: Long,
)
