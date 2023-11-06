package com.sadig.spendtracker.domain.usecase

import com.sadig.spendtracker.data.source.local.UserPreferencesDataSourceImpl
import com.sadig.spendtracker.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCurrencyInteractor @Inject constructor(val dataStoreRepository: DataStoreRepository) {
    operator fun invoke(): Flow<String?> {
        return dataStoreRepository.read(UserPreferencesDataSourceImpl.KEY_CURRENCY)
    }
}