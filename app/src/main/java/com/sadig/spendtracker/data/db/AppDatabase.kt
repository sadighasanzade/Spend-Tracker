package com.sadig.spendtracker.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sadig.spendtracker.data.model.Spending
import javax.inject.Inject

@Database(entities = [Spending::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {
    abstract fun spendingDao(): SpendingDAO
}