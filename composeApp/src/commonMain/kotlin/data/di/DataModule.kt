package data.di

import data.local.di.localDataModule
import data.remote.di.remoteDataModule
import data.repository.QuestRepositoryImpl
import domain.repository.ChallengeRepository
import org.koin.dsl.module

val dataModule = module {
    includes(remoteDataModule, localDataModule)
    single<ChallengeRepository> {
        QuestRepositoryImpl(questLocalDataSource = get(), questRemoteDataSource = get())
    }
}