package com.example.trendingtoday.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.trendingtoday.data.db.NewsArticleDao
import com.example.trendingtoday.data.db.NewsArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideNewsArticleDatabase(application: Application): NewsArticleDatabase =
        Room.databaseBuilder(application, NewsArticleDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideNewsArticleDao(newsArticleDatabase: NewsArticleDatabase): NewsArticleDao =
        newsArticleDatabase.getNewsArticleDao()
}