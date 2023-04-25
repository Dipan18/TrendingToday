package com.example.trendingtoday.domain.usecase

import com.example.trendingtoday.domain.repository.NewsArticlesRepository

class GetNewsArticlesUseCase(private val newsArticlesRepository: NewsArticlesRepository) {
    suspend fun execute(country: String, pageNo: Int) =
        newsArticlesRepository.getNewsArticles(country, pageNo)
}