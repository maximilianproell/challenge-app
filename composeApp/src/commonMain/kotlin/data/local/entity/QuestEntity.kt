package data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val wasCompletedByUser: Boolean,
    // For simplicity, we have this value here. Should be moved to shared prefs at same point
    val isCurrentlyActive: Boolean,
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
)