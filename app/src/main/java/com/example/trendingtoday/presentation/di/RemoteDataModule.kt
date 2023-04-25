package com.example.trendingtoday.presentation.di

import com.example.trendingtoday.data.api.NewsAPIService
import com.example.trendingtoday.data.repository.datasource.NewsRemoteDataSource
import com.example.trendingtoday.data.repository.datasourceimpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsAPIService: NewsAPIService): NewsRemoteDataSource =
        NewsRemoteDataSourceImpl(newsAPIService)
}