package com.example.trendingtoday.presentation.di

import com.example.trendingtoday.data.repository.NewsArticlesRepositoryImpl
import com.example.trendingtoday.data.repository.datasource.NewsLocalDataSource
import com.example.trendingtoday.data.repository.datasource.NewsRemoteDataSource
import com.example.trendingtoday.domain.repository.NewsArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNewsArticlesRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsArticlesRepository =
        NewsArticlesRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
}