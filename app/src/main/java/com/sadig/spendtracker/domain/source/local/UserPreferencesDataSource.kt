package com.sadig.spendtracker.domain.source.local

import kotlinx.coroutines.flow.Flow

interface UserPreferencesDataSource {
    suspend fun save(key: String, value: String)
    fun read(key: String): Flow<String?>
}