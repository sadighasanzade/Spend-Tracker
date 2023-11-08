package com.sadig.spendtracker.domain.usecase

import com.sadig.spendtracker.data.model.Spending
import com.sadig.spendtracker.domain.repository.SpendingRepository
import javax.inject.Inject

class PutSpendingInteractor @Inject constructor(private val spendingRepository: SpendingRepository) {
    operator fun invoke(spending: Spending) {
        spendingRepository.putSpending(spending = spending)
    }
}