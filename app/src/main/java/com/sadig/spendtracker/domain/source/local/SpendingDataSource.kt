package com.sadig.spendtracker.domain.source.local

import com.sadig.spendtracker.data.model.Spending
import com.sadig.spendtracker.data.model.query.GetSpendingQuery

interface SpendingDataSource {
    suspend fun putSpending(spending: Spending)
    suspend fun getSpendings(query: GetSpendingQuery): List<Spending>
}