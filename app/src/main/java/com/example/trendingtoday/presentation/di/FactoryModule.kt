package com.example.trendingtoday.presentation.di

import android.app.Application
import com.example.trendingtoday.domain.usecase.*
import com.example.trendingtoday.presentation.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsArticlesUseCase: GetNewsArticlesUseCase,
        searchNewsArticlesUseCase: SearchNewsArticlesUseCase,
        saveNewsArticlesUseCase: SaveNewsArticlesUseCase,
        getSavedNewsArticlesUseCase: GetSavedNewsArticlesUseCase,
        deleteSavedNewsArticlesUseCase: DeleteSavedNewsArticlesUseCase
    ): NewsViewModelFactory =
        NewsViewModelFactory(
            application,
            getNewsArticlesUseCase,
            searchNewsArticlesUseCase,
            saveNewsArticlesUseCase,
            getSavedNewsArticlesUseCase,
            deleteSavedNewsArticlesUseCase
        )
}