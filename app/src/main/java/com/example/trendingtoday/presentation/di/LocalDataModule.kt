package com.example.trendingtoday.presentation.di

import com.example.trendingtoday.data.db.NewsArticleDao
import com.example.trendingtoday.data.repository.datasource.NewsLocalDataSource
import com.example.trendingtoday.data.repository.datasourceimpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Singleton
    @Provides
    fun provideNewsLocalDataSource(newsArticleDao: NewsArticleDao): NewsLocalDataSource =
        NewsLocalDataSourceImpl(newsArticleDao)
}