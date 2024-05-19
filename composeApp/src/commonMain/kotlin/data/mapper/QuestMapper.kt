package data.mapper

import data.local.entity.QuestEntity
import data.local.entity.QuestRemoteUpdate
import data.remote.model.QuestDto
import domain.model.Quest
import domain.model.QuestActivationInfo

fun QuestDto.toEntity(): QuestEntity {
    return QuestEntity(
        id = id,
        category = category,
        name = nameEn,
        isVisibleOnMap = isVisibleOnMap,
        isDaily = isDaily,
        description = descriptionEn,
        xp = xp,
        timeToComplete = timeToComplete,
        activationGeoLocation = activationGeoLocation.toGeoLocation(),
        wasCompletedByUser = false,
    )
}

fun QuestDto.toDbUpdate(): QuestRemoteUpdate {
    return QuestRemoteUpdate(
        id = id,
        category = category,
        name = nameEn,
        isVisibleOnMap = isVisibleOnMap,
        isDaily = isDaily,
        description = descriptionEn,
        xp = xp,
        timeToComplete = timeToComplete,
        activationGeoLocation = activationGeoLocation.toGeoLocation(),
    )
}

/**
 * Converts this [QuestEntity] to the corresponding domain model object.
 * @param questActivationInfo If not null, this [Quest] is currently tackled by the user.
 * @param timeLeftToComplete The time in minutes left for the user to finish the quest.
 * @param isClickable states if this [Quest] is clickable on the UI. If [questActivationInfo] is non null,
 * this value set here is ignored and the [Quest] is always clickable.
 */
fun QuestEntity.toDomain(
    questActivationInfo: QuestActivationInfo?,
    timeLeftToComplete: Int?,
    isClickable: Boolean,
): Quest {
    return Quest(
        id = id,
        category = category,
        name = name,
        isVisibleOnMap = isVisibleOnMap,
        isDaily = isDaily,
        description = description,
        xp = xp,
        timeToComplete = timeLeftToComplete,
        activationGeoLocation = activationGeoLocation,
        activationInfo = questActivationInfo,
        isClickable = if (questActivationInfo != null) true else isClickable,
    )
}