package data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import domain.model.GeoLocation

@Entity(tableName = "quest_table")
data class QuestEntity(
    @PrimaryKey val id: String,
    val category: String,
    val name: String,
    val isVisibleOnMap: Boolean,
    val isDaily: Boolean,
    val description: String,
    val xp: Int,
    val timeToComplete: Int?,
    val activationGeoLocation: GeoLocation,
    val completionData: String,
    val wasCompletedByUser: Boolean,
)

data class QuestEntityWithActivationInfo(
    @Embedded val questEntity: QuestEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questId",
    )
    val activeInfo: ActiveQuestEntity?
)

/**
 * This update class is only needed since some fields in the database don't exist in the remote database yet.
 * We therefore do a partial update. This class may be deleted in the future, once user properties are saved remotely.
 */
data class QuestRemoteUpdate(
    val id: String,
    val category: String,
    val name: String,
    val isVisibleOnMap: Boolean,
    val isDaily: Boolean,
    val description: String,
    val xp: Int,
    val timeToComplete: Int?,
    val activationGeoLocation: GeoLocation,
    val completionData: String,
)