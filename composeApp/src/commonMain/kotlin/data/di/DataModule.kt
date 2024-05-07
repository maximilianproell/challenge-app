package data.di

import data.remote.di.remoteDataModule
import data.repository.ChallengeRepositoryImpl
import domain.repository.ChallengeRepository
import org.koin.dsl.module

val dataModule = module {
    includes(remoteDataModule)
    single<ChallengeRepository> { ChallengeRepositoryImpl(get()) }
}