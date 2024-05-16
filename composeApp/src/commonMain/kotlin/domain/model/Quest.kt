package domain.model

data class Quest(
    val id: String,
    val category: String,
    val name: String,
    val isVisibleOnMap: Boolean,
    val isDaily: Boolean,
    val description: String,
    val xp: Int,
    val timeToComplete: Int?,
    val activationGeoLocation: GeoLocation,
    val activationInfo: QuestActivationInfo?,
    val isClickable: Boolean,
)

data class QuestActivationInfo(
    val activationTimeStamp: Long,
)