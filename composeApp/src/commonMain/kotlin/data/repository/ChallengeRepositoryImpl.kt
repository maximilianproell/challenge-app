package data.repository

import data.remote.ChallengeRemoteDataSource
import domain.model.Quest
import domain.model.GeoLocation
import domain.repository.ChallengeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ChallengeRepositoryImpl(
    private val challengeRemoteDataSource: ChallengeRemoteDataSource
): ChallengeRepository {

    // TODO: Implement actual cashing.
    override fun observeVisibleChallenges(): Flow<List<Quest>> {
        return flow {
            val list = challengeRemoteDataSource.getAllChallenges()
            emit(
                list.map {
                    val (latitude, longitude) = it.activationGeoLocation.split(",").map { it.dropLast(1).toDouble() }
                    Quest(
                        id = it.id,
                        category = it.category,
                        name = it.nameEn,
                        isVisibleOnMap = it.isVisibleOnMap,
                        isDaily = it.isDaily,
                        description = it.descriptionEn,
                        xp = it.xp,
                        timeToComplete = it.timeToComplete,
                        activationGeoLocation = GeoLocation(
                            latitude = latitude,
                            longitude = longitude,
                        )
                    )
                }
            )
        }
    }

    override suspend fun activateChallenge(quest: Quest, activationCode: String) {
        // TODO: implement
    }

    override suspend fun completeChallenge(challengeId: String, completionCode: String) {
        // TODO: implement
    }
}