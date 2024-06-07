package data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import data.local.converter.Converters
import data.local.dao.ActiveQuestDao
import data.local.dao.QuestDao
import data.local.dao.UserDao
import data.local.entity.ActiveQuestEntity
import data.local.entity.QuestEntity
import data.local.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

const val DATABASE_NAME = "quest_app_room.db"

@Database(entities = [QuestEntity::class, ActiveQuestEntity::class, UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getQuestDao(): QuestDao

    abstract fun getActiveQuestDao(): ActiveQuestDao
    abstract fun getUserDao(): UserDao
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

expect class DataBaseFactory() {
    fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
}
