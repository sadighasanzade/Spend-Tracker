package com.sadig.spendtracker.utils

import java.util.Calendar
import java.util.Date

object Utils {
    val DEFAULT_CURRENCY = "Eur"
    fun getMonthYear(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        return "$year-$month"
    }
}