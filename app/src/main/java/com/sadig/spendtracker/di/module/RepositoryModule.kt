package com.sadig.spendtracker.di.module

import com.sadig.spendtracker.data.repository.DataStoreRepositoryImpl
import com.sadig.spendtracker.data.repository.SpendingRepositoryImpl
import com.sadig.spendtracker.domain.repository.DataStoreRepository
import com.sadig.spendtracker.domain.repository.SpendingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindDataStoreRepository(
        repositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository

    @Singleton
    @Binds
    abstract fun bindsSpendingRepository(
        repositoryImpl: SpendingRepositoryImpl
    ): SpendingRepository
}