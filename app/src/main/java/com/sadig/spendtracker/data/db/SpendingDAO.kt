package com.sadig.spendtracker.data.db

import androidx.room.Dao
import androidx.room.Insert
import com.sadig.spendtracker.data.model.Spending

@Dao
interface SpendingDAO {
    @Insert
    fun insertAll(vararg spendings: Spending)

}