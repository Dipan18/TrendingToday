package com.example.trendingtoday.domain.usecase

import com.example.trendingtoday.domain.repository.NewsArticlesRepository

class GetSavedNewsArticlesUseCase(private val newsArticlesRepository: NewsArticlesRepository) {
    fun execute() = newsArticlesRepository.getSavedNewsArticles()
}