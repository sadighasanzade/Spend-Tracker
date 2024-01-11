package com.sadig.spendtracker.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.sadig.spendtracker.data.db.SpendingDAO
import com.sadig.spendtracker.data.source.local.SpendingDataSourceImpl
import com.sadig.spendtracker.data.source.local.UserPreferencesDataSourceImpl
import com.sadig.spendtracker.domain.repository.DataStoreRepository
import com.sadig.spendtracker.domain.repository.SpendingRepository
import com.sadig.spendtracker.domain.source.local.SpendingDataSource
import com.sadig.spendtracker.domain.source.local.UserPreferencesDataSource
import com.sadig.spendtracker.domain.usecase.GetSpendingsUseCase
import com.sadig.spendtracker.domain.usecase.PutCurrencyUseCase
import com.sadig.spendtracker.domain.usecase.PutSpendingUseCase
import com.sadig.spendtracker.domain.usecase.ReadCurrencyUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpendTrackerModule {

    @Provides
    @Singleton
    fun providesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(UserPreferencesDataSourceImpl.USER_PREFERENCE_KEY.name)
        }
    }

    @Provides
    @Singleton
    fun providesSpendingDataSource(spendingDAO: SpendingDAO): SpendingDataSource {
        return SpendingDataSourceImpl(spendingDAO)
    }

    @Provides
    @Singleton
    fun providesUserPreferencesDataSource(dataStore: DataStore<Preferences>): UserPreferencesDataSource {
        return UserPreferencesDataSourceImpl(dataStore)
    }

    @Provides
    fun providesGetCurrencyInteractor(dataStoreRepository: DataStoreRepository): ReadCurrencyUseCase {
        return ReadCurrencyUseCase(dataStoreRepository)
    }

    @Provides
    fun providesPutCurrencyInteractor(dataStoreRepository: DataStoreRepository): PutCurrencyUseCase {
        return PutCurrencyUseCase(dataStoreRepository)
    }

    @Provides
    fun providesPutSpendingInteractor(spendingRepository: SpendingRepository): PutSpendingUseCase {
        return PutSpendingUseCase(spendingRepository)
    }

    @Provides
    fun providesGetSpendingsInteractor(spendingRepository: SpendingRepository): GetSpendingsUseCase {
        return GetSpendingsUseCase(spendingRepository)
    }
}
