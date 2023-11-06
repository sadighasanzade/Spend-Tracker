package com.sadig.spendtracker.domain.repository

import com.sadig.spendtracker.domain.source.local.UserPreferencesDataSource
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun save(key: String, value: String)
    fun read(key: String): Flow<String?>
}