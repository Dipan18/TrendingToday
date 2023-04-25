package com.example.trendingtoday.data.db

import androidx.room.TypeConverter
import com.example.trendingtoday.data.model.Publisher

class Converters {
    @TypeConverter
    fun fromPublisher(publisher: Publisher) = publisher.name

    @TypeConverter
    fun toPublisher(publisherName: String) = Publisher(publisherName, publisherName)
}