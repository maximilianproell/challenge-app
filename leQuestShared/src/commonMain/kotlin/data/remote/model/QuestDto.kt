package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestDto(
    val id: String,
    @SerialName("created_at")
    val createdAt: String,
    val category: String,
    val city: String,
    @SerialName("name_en")
    val nameEn: String,
    val hashtag: String,
    @SerialName("is_daily")
    val isDaily: Boolean,
    @SerialName("description_en")
    val descriptionEn: String,
    val difficulty: String,
    val xp: Int,
    @SerialName("time_to_complete")
    val timeToComplete: Int?,
    @SerialName("activation_geo_location")
    val activationGeoLocation: String,
    @SerialName("activation_method")
    val activationMethod: String?,
    @SerialName("activation_data")
    val activationData: String?,
    @SerialName("completion_geo_location")
    val completionGeoLocation: String?,
    @SerialName("completion_data")
    val completionData: String,
    @SerialName("is_visible_on_map")
    val isVisibleOnMap: Boolean,
    @SerialName("time_locked")
    val timeLocked: Int?,
)
