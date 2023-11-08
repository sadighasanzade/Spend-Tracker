package com.sadig.spendtracker.domain.repository

import com.sadig.spendtracker.data.model.Spending

interface SpendingRepository {
    fun putSpending(spending: Spending)
}