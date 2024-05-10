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

fun QuestEntity.toDomain(): Quest {
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
        isCurrentlyActive = false,
    )
}