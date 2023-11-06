package com.sadig.spendtracker.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sadig.spendtracker.domain.repository.DataStoreRepository
import com.sadig.spendtracker.domain.source.local.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class DataStoreRepositoryImpl @Inject constructor(private val dataSource: UserPreferencesDataSource) :
    DataStoreRepository {
    override suspend fun save(key: String, value: String) {
        dataSource.save(key, value)
    }

    override fun read(key: String): Flow<String?> {
        return dataSource.read(key)

    }

}