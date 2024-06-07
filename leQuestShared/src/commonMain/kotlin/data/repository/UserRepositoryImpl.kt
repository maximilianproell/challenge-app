package data.repository

import data.local.UserLocalDataSource
import data.local.entity.UserEntity
import data.mapper.toDomain
import domain.model.User
import domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class UserRepositoryImpl(
    private val applicationScope: CoroutineScope,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {

    init {
        applicationScope.launch {
            val localUser = userLocalDataSource.getUser()
            if (localUser == null) {
                // We create a standard user if there is no user yet:
                userLocalDataSource.insertUser(
                    UserEntity(
                        // TODO: This should be set by the user at some point.
                        name = "Max Mustermann",
                        xp = 0,
                    )
                )
            }
        }
    }

    override fun observeUser(): Flow<User> {
        return userLocalDataSource.observeUser().map { it.toDomain() }
    }
}