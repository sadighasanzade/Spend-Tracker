package com.sadig.spendtracker.domain.repository

import com.sadig.spendtracker.data.model.Spending
import com.sadig.spendtracker.data.model.query.GetSpendingQuery

interface SpendingRepository {
    suspend fun putSpending(spending: Spending)
    suspend fun getSpending(query: GetSpendingQuery): List<Spending>
}