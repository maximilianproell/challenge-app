package data.mapper

import data.local.entity.QuestEntity
import data.local.entity.QuestRemoteUpdate
import data.remote.model.QuestDto
import domain.model.Quest

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
        isCurrentlyActive = false,
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
 * @param isClickable states if this [Quest] is clickable on the UI. If [Quest.isCurrentlyActive] is true,
 * this value set here is ignored and the [Quest] is always clickable.
 */
fun QuestEntity.toDomain(
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
        timeToComplete = timeToComplete,
        activationGeoLocation = activationGeoLocation,
        isCurrentlyActive = isCurrentlyActive,
        isClickable = if (isCurrentlyActive) true else isClickable,
    )
}