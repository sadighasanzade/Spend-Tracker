package com.sadig.spendtracker.di.module

import android.content.Context
import androidx.room.Room
import com.sadig.spendtracker.data.db.AppDatabase
import com.sadig.spendtracker.data.db.SpendingDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "spendings_db"
        ).allowMainThreadQueries().build()
    }
    @Provides
    @Singleton
    fun provides(db: AppDatabase): SpendingDAO {
        return db.spendingDao()
    }
}