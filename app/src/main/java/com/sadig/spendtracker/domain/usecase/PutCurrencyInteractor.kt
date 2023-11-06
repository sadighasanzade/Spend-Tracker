package com.sadig.spendtracker.domain.usecase

import androidx.datastore.core.DataStore
import com.sadig.spendtracker.data.source.local.UserPreferencesDataSourceImpl
import com.sadig.spendtracker.domain.repository.DataStoreRepository
import javax.inject.Inject

class PutCurrencyInteractor @Inject constructor(private val dataStoreRepository: DataStoreRepository) {
    suspend operator fun invoke(currency: String) =
        dataStoreRepository.save(UserPreferencesDataSourceImpl.KEY_CURRENCY, currency)
}