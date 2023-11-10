package com.sadig.spendtracker.domain.repository

import com.sadig.spendtracker.data.model.Spending

interface SpendingRepository {
    suspend fun putSpending(spending: Spending)
}