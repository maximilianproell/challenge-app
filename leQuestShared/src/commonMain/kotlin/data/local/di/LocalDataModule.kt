package data.local.di

import data.local.AppDatabase
import data.local.DataBaseFactory
import data.local.QuestLocalDataSource
import data.local.QuestLocalDataSourceImpl
import data.local.getRoomDatabase
import org.koin.dsl.module

val localDataModule = module {
    single<AppDatabase> {
        val factory = DataBaseFactory()
        getRoomDatabase(factory.getRoomDatabaseBuilder())
    }
    single<QuestLocalDataSource> {
        val appDatabase: AppDatabase = get()
        QuestLocalDataSourceImpl(
            questDao = appDatabase.getQuestDao(),
            activeQuestDao = appDatabase.getActiveQuestDao(),
        )
    }
}