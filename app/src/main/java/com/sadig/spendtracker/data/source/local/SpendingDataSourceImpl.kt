package com.sadig.spendtracker.data.source.local

import android.util.Log
import com.sadig.spendtracker.data.db.SpendingDAO
import com.sadig.spendtracker.data.model.Spending
import com.sadig.spendtracker.domain.source.local.SpendingDataSource
import javax.inject.Inject

class SpendingDataSourceImpl @Inject constructor(val spendingDAO: SpendingDAO) : SpendingDataSource {
    override suspend fun putSpending(spending: Spending) {
        spendingDAO.insertAll(spending)
    }

    override suspend fun getSpendingsOfThisMonth(startDate: Long): List<Spending> {
        return  spendingDAO.getSpendngsOfThisMonth(startDate)
    }

    override suspend fun getSpendingsMonthByMonth(): List<Spending> {
        return spendingDAO.getAll()
    }

}