package data.remote.di

import data.remote.QuestRemoteDataSource
import data.remote.QuestRemoteDataSourceImpl
import org.koin.dsl.module

val remoteDataModule = module {
    single<QuestRemoteDataSource> { QuestRemoteDataSourceImpl() }
}