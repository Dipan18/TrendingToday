package com.example.trendingtoday.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trendingtoday.domain.usecase.*

class NewsViewModelFactory(
    private val application: Application,
    private val getNewsArticlesUseCase: GetNewsArticlesUseCase,
    private val getSearchNewsArticlesUseCase: SearchNewsArticlesUseCase,
    private val saveNewsArticlesUseCase: SaveNewsArticlesUseCase,
    private val getSavedNewsArticlesUseCase: GetSavedNewsArticlesUseCase,
    private val deleteSavedNewsArticlesUseCase: DeleteSavedNewsArticlesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            application,
            getNewsArticlesUseCase,
            getSearchNewsArticlesUseCase,
            saveNewsArticlesUseCase,
            getSavedNewsArticlesUseCase,
            deleteSavedNewsArticlesUseCase
        ) as T
    }
}