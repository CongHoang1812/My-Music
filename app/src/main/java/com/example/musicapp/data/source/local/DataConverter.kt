package com.example.musicapp.data.source.local

import androidx.media3.common.FileTypes.Type
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.Date

object DataConverter {
    @TypeConverter
    fun fromDate(date: Date?):Long?{
        return date?.time?: 0
    }
    @TypeConverter
    fun  toDate(millis: Long): Date{
        return Date(millis)
    }
}