package data.local

import data.local.dao.UserDao
import data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun observeUser(): Flow<UserEntity>

    suspend fun getUser(): UserEntity?

    suspend fun insertUser(user: UserEntity)

}

class UserLocalDataSourceImpl(
    private val userDao: UserDao
) : UserLocalDataSource {
    override fun observeUser(): Flow<UserEntity> = userDao.observeUser()

    override suspend fun getUser(): UserEntity? = userDao.getUser()

    override suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
}