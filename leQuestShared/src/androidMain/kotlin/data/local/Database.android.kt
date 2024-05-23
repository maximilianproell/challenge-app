package data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

actual class DataBaseFactory : KoinComponent {
    actual fun getRoomDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
        val appContext: Context = get()
        val dbFile = appContext.getDatabasePath(DATABASE_NAME)
        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}