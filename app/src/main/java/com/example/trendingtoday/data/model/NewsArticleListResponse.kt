package com.example.trendingtoday.data.model


data class NewsArticleListResponse(
    var articles: List<NewsArticle>,
    val status: String,
    var totalResults: Int
)