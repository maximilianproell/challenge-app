package data.remote.di

import data.remote.ChallengeRemoteDataSource
import data.remote.ChallengeRemoteDataSourceImpl
import org.koin.dsl.module

val remoteDataModule = module {
    single<ChallengeRemoteDataSource> { ChallengeRemoteDataSourceImpl() }
}