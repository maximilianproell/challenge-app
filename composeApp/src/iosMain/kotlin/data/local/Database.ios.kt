package data.local

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

actual class DataBaseFactory {
    actual fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val dbFilePath = NSHomeDirectory() + "/$DATABASE_NAME"
        return Room.databaseBuilder<AppDatabase>(
            name = dbFilePath,
            factory =  { AppDatabase::class.instantiateImpl() }
        )
    }
}