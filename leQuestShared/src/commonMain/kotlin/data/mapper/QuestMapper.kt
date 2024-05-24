package data.mapper

import data.local.entity.QuestEntity
import data.local.entity.QuestEntityWithActivationInfo
import data.local.entity.QuestRemoteUpdate
import data.remote.model.QuestDto
import domain.model.Quest
import domain.model.QuestActivationInfo
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.milliseconds

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
        completionData = completionData,
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
        completionData = completionData,
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
        completionData = completionData,
    )
}

/**
 * Converts this [QuestEntityWithActivationInfo] to the corresponding domain model object.
 */
inline fun QuestEntityWithActivationInfo.toDomain(
    isAtLeastOneQuestActive: Boolean,
    onTimeRanOut: () -> Unit,
): Quest {
    val timeLeftToComplete = questEntity.timeToComplete?.let { timeToCompleteInMinutes ->
        val activationInfo = activeInfo

        if (activationInfo == null) timeToCompleteInMinutes else {
            val timePassed =
                (Clock.System.now().toEpochMilliseconds() - activationInfo.startTimestamp)
                    .milliseconds
                    .inWholeMinutes
            (timeToCompleteInMinutes - timePassed).toInt()
        }
    }

    if (timeLeftToComplete != null) {
        if (timeLeftToComplete <= 0) {
            onTimeRanOut()
        }
    }

    return questEntity.toDomain(
        questActivationInfo = activeInfo?.let { activeInfo ->
            QuestActivationInfo(
                activationTimeStampMilliseconds = activeInfo.startTimestamp,
            )
        },
        // Only clickable, if no other quest is active.
        isClickable = !isAtLeastOneQuestActive,
        timeLeftToComplete = timeLeftToComplete,
    )
}