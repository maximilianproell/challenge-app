package data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table LIMIT 1")
    fun observeUser(): Flow<UserEntity>

    @Insert
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Update
    suspend fun updateUser(userEntity: UserEntity)

}