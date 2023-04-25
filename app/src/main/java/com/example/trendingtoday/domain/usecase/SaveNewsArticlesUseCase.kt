package com.example.trendingtoday.domain.usecase

import com.example.trendingtoday.data.model.NewsArticle
import com.example.trendingtoday.domain.repository.NewsArticlesRepository

class SaveNewsArticlesUseCase(private val newsArticlesRepository: NewsArticlesRepository) {
    suspend fun execute(newsArticle: NewsArticle) = newsArticlesRepository.saveNewsArticle(newsArticle)
}