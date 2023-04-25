package com.example.trendingtoday.presentation.di

import com.example.trendingtoday.presentation.adapter.NewsListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Singleton
    @Provides
    fun provideNewsListAdapter(): NewsListAdapter = NewsListAdapter()
}