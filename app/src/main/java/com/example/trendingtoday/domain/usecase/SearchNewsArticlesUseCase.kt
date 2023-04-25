package com.example.trendingtoday.domain.usecase

import com.example.trendingtoday.domain.repository.NewsArticlesRepository

class SearchNewsArticlesUseCase(private val newsArticlesRepository: NewsArticlesRepository) {
    suspend fun execute(searchQuery: String, pageNo: Int) =
        newsArticlesRepository.getNewsArticleSearchResults(searchQuery, pageNo)
}