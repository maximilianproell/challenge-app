package data.di

import data.local.di.localDataModule
import data.remote.di.remoteDataModule
import data.repository.QuestRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.QuestsRepository
import domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(remoteDataModule, localDataModule)
    single(named("IoCoroutineScope")) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
    single<QuestsRepository> {
        QuestRepositoryImpl(questLocalDataSource = get(), questRemoteDataSource = get())
    }
    single<UserRepository> {
        UserRepositoryImpl(
            applicationScope = get(named("IoCoroutineScope")),
            userLocalDataSource = get()
        )
    }
}