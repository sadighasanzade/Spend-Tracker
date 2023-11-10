package com.sadig.spendtracker.data.source.local

import com.sadig.spendtracker.data.db.SpendingDAO
import com.sadig.spendtracker.data.model.Spending
import com.sadig.spendtracker.domain.source.local.SpendingDataSource
import javax.inject.Inject

class SpendingDataSourceImpl @Inject constructor(val spendingDAO: SpendingDAO) : SpendingDataSource {
    override suspend fun putSpending(spending: Spending) {
        spendingDAO.insertAll(spending)
    }

}