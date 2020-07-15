package com.example.matsubajun.githubsample.data.db

import androidx.annotation.Nullable
import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun fromTimeToDate(@Nullable value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun fromDateToTime(@Nullable date: Date?): Long? {
        return if (date == null) null else date.time
    }
}