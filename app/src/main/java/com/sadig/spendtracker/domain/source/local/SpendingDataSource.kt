package com.sadig.spendtracker.domain.source.local

import com.sadig.spendtracker.data.model.Spending

interface SpendingDataSource {
    fun putSpending(spending: Spending)
}