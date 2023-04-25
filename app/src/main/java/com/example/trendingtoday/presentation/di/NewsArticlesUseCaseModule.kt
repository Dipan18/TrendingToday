package com.example.trendingtoday.presentation.di

import com.example.trendingtoday.domain.repository.NewsArticlesRepository
import com.example.trendingtoday.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsArticlesUseCaseModule {
    @Singleton
    @Provides
    fun provideGetNewsArticlesUseCase(newsArticlesRepository: NewsArticlesRepository): GetNewsArticlesUseCase =
        GetNewsArticlesUseCase(newsArticlesRepository)

    @Singleton
    @Provides
    fun provideSearchNewsArticlesUseCase(newsArticlesRepository: NewsArticlesRepository): SearchNewsArticlesUseCase =
        SearchNewsArticlesUseCase(newsArticlesRepository)

    @Singleton
    @Provides
    fun provideSaveNewsArticleUseCase(newsArticlesRepository: NewsArticlesRepository): SaveNewsArticlesUseCase =
        SaveNewsArticlesUseCase(newsArticlesRepository)

    @Singleton
    @Provides
    fun provideGetSavedNewsArticlesUseCase(newsArticlesRepository: NewsArticlesRepository): GetSavedNewsArticlesUseCase =
        GetSavedNewsArticlesUseCase(newsArticlesRepository)

    @Singleton
    @Provides
    fun provideDeleteSavedArticleUseCase(newsArticlesRepository: NewsArticlesRepository): DeleteSavedNewsArticlesUseCase =
        DeleteSavedNewsArticlesUseCase(newsArticlesRepository)
}