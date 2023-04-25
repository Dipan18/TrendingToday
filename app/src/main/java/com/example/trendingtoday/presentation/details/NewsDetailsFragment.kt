package com.example.trendingtoday.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.trendingtoday.MainActivity
import com.example.trendingtoday.data.model.NewsArticle
import com.example.trendingtoday.databinding.FragmentNewsDetailsBinding
import com.example.trendingtoday.presentation.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class NewsDetailsFragment : Fragment() {
    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = arguments?.getSerializable("news_article") as NewsArticle
        viewModel = (activity as MainActivity).viewModel

        binding.wvNewsArticleDetails.apply {
            val client = WebViewClient()
            if (article.url.isNotBlank()) {
                loadUrl(article.url)
            }
        }

        binding.fabSaveNewsArticle.setOnClickListener {
            viewModel.saveNewsArticleInDB(article)
            Snackbar.make(view, "Saved!", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}