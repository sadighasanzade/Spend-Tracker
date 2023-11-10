package com.sadig.spendtracker.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.sadig.spendtracker.data.model.type_converter.SpendingTypeConverter
import java.util.Date

@Entity
@TypeConverters(SpendingTypeConverter::class)
data class Spending(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "date") val date: Date,
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

}
