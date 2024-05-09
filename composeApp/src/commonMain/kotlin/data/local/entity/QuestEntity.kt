package data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import domain.model.GeoLocation

@Entity
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