package com.example.trendingtoday.domain.usecase

import com.example.trendingtoday.data.model.NewsArticle
import com.example.trendingtoday.domain.repository.NewsArticlesRepository

class DeleteSavedNewsArticlesUseCase(private val newsArticlesRepository: NewsArticlesRepository) {
    suspend fun execute(newsArticle: NewsArticle) = newsArticlesRepository.deleteNewsArticle(newsArticle)
}