package com.sadig.spendtracker.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sadig.spendtracker.data.model.Spending

@Dao
interface SpendingDAO {
    @Insert
    fun insertAll(vararg spendings: Spending)

    @Query("SELECT * FROM spending WHERE date >= :startDate")
    fun getSpendngsOfThisMonth(startDate: Long): List<Spending>

    @Query("SELECT * FROM spending")
    fun getAll(): List<Spending>
}