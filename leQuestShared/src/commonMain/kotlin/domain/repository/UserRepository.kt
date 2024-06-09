package domain.repository

import domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun observeUser(): Flow<User>

    suspend fun increaseXpOfUser(additionalXp: Int)
}