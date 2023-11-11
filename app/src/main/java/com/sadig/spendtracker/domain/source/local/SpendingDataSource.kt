package com.sadig.spendtracker.domain.source.local

import com.sadig.spendtracker.data.model.Spending

interface SpendingDataSource {
    suspend fun putSpending(spending: Spending)
    suspend fun getSpendingsOfThisMonth(startDate: Long): List<Spending>
    suspend fun getSpendingsMonthByMonth(): List<Spending>
}