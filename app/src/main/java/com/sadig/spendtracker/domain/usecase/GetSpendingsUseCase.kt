package com.sadig.spendtracker.domain.usecase

import com.sadig.spendtracker.data.model.Spending
import com.sadig.spendtracker.data.model.query.GetSpendingQuery
import com.sadig.spendtracker.domain.repository.SpendingRepository
import com.sadig.spendtracker.ui.viewmodel.HomeViewModel
import com.sadig.spendtracker.utils.Utils
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

class GetSpendingsUseCase @Inject constructor(val spendingRepository: SpendingRepository) {
    suspend operator fun invoke(fetchType: HomeViewModel.FetchType): List<Spending> {
        val month = LocalDate.now().month
        val year = LocalDate.now().year
        val firstDayOfMonth = LocalDate.of(year, month, 1).atStartOfDay(ZoneId.systemDefault())
        return when (fetchType) {
            HomeViewModel.FetchType.ThisMonth -> spendingRepository.getSpending(
                GetSpendingQuery(
                    true,
                    firstDayOfMonth.toEpochSecond() * 1000
                )
            ).sortedByDescending { it.date }

            HomeViewModel.FetchType.MonthByMonth -> {

                val allItems = spendingRepository.getSpending(GetSpendingQuery(false, 0))
                    .sortedByDescending { it.date }
                val groupedByMonth = allItems.groupBy { Utils.getMonthYear(it.date) }

                val result = groupedByMonth.map { (monthYear, item) ->
                    val totalAmount = item.sumOf { it.amount }
                    Spending(
                        title = monthYear,
                        description = "Spendings of $monthYear",
                        amount = totalAmount,
                        date = Date()
                    )
                }
                return result
            }
        }
    }
}