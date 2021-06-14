package com.example.dolist.data

import androidx.room.TypeConverter
import com.example.dolist.data.models.Priority

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }
}