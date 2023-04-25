package com.example.trendingtoday.presentation.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingtoday.MainActivity
import com.example.trendingtoday.R
import com.example.trendingtoday.data.model.NewsArticle
import com.example.trendingtoday.databinding.FragmentSavedNewsArticlesBinding
import com.example.trendingtoday.presentation.NewsViewModel
import com.example.trendingtoday.presentation.adapter.NewsListAdapter
import com.google.android.material.snackbar.Snackbar

class SavedNewsArticlesFragment() : Fragment() {
    private var _binding: FragmentSavedNewsArticlesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedNewsArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        newsListAdapter = (activity as MainActivity).newsListAdapter

        viewModel.getSavedNewsArticles().observe(viewLifecycleOwner) {
            newsListAdapter.differ.submitList(it)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvSavedNewsArticles.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        newsListAdapter.setOnClickListener { navigateToNewsDetailsFragment(it) }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.rvSavedNewsArticles)
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val article = newsListAdapter.differ.currentList[viewHolder.bindingAdapterPosition]
            deleteNewsArticle(article)
        }
    }

    private fun navigateToNewsDetailsFragment(it: NewsArticle) {
        val bundle = Bundle().apply { putSerializable("news_article", it) }
        findNavController().navigate(
            R.id.action_savedNewsArticlesFragment_to_newsDetailsFragment,
            bundle
        )
    }

    private fun deleteNewsArticle(article: NewsArticle) {
        viewModel.deleteSavedNewsArticle(article)

        Snackbar.make(requireView(), "Deleted!", Snackbar.LENGTH_SHORT).setAction("Undo") {
            viewModel.saveNewsArticleInDB(article)
        }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}