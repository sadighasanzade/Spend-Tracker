package com.sadig.spendtracker.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sadig.spendtracker.domain.source.local.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSourceImpl
    @Inject constructor(private val dataStore: DataStore<Preferences>): UserPreferencesDataSource {
    companion object {
        val USER_PREFERENCE_KEY = stringPreferencesKey("user_preference")
        val KEY_CURRENCY = "CURRENCY_KEY"
    }
    override suspend fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
         dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    override fun read(key: String): Flow<String?> {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data
            .map { preferences -> preferences[dataStoreKey] ?: "" }
        return preferences    }

}