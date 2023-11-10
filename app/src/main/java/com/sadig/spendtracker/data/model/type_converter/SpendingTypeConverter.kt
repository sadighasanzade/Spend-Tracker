package com.sadig.spendtracker.data.model.type_converter

import androidx.room.TypeConverter
import java.util.Date

public class SpendingTypeConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}