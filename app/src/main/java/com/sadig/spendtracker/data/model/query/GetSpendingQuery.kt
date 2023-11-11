package com.sadig.spendtracker.data.model.query

data class GetSpendingQuery(
    val takeMonthByMonth: Boolean = false,
    val startDate: Long
)
