package com.example.trendingtoday.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trendingtoday.data.model.NewsArticle

@Database(
    entities = [NewsArticle::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class NewsArticleDatabase : RoomDatabase() {
    abstract fun getNewsArticleDao(): NewsArticleDao
}