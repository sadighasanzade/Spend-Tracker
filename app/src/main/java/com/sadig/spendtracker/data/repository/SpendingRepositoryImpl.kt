package com.sadig.spendtracker.data.repository

import com.sadig.spendtracker.data.model.Spending
import com.sadig.spendtracker.data.model.query.GetSpendingQuery
import com.sadig.spendtracker.domain.repository.SpendingRepository
import com.sadig.spendtracker.domain.source.local.SpendingDataSource
import javax.inject.Inject

class SpendingRepositoryImpl @Inject constructor(val spendingDataSource: SpendingDataSource) :
    SpendingRepository {
    override suspend fun putSpending(spending: Spending) {
        spendingDataSource.putSpending(spending = spending)
    }

    override suspend fun getSpending(query: GetSpendingQuery): List<Spending> {
        return spendingDataSource.getSpendings(query)
    }
}