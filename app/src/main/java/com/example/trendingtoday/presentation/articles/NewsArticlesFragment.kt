package com.example.trendingtoday.presentation.articles

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trendingtoday.MainActivity
import com.example.trendingtoday.R
import com.example.trendingtoday.data.util.Resource
import com.example.trendingtoday.databinding.FragmentNewsArticlesBinding
import com.example.trendingtoday.presentation.NewsViewModel
import com.example.trendingtoday.presentation.adapter.NewsListAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsArticlesFragment : Fragment(), MenuProvider {
    private var _binding: FragmentNewsArticlesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsListAdapter: NewsListAdapter

    private var currentPage = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsArticlesBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsListAdapter = (activity as MainActivity).newsListAdapter

        initRecyclerView()
        displayNewsArticles()
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            loadNextPage()
        }
    }

    private fun initRecyclerView() {
        binding.rvNewsFeed.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(onScrollListener)
        }

        newsListAdapter.setOnClickListener {
            val bundle = Bundle().apply { putSerializable("news_article", it) }
            findNavController().navigate(
                R.id.action_newsArticlesFragment_to_newsDetailsFragment,
                bundle
            )
        }
    }

    private fun displayNewsArticles() {
        viewModel.getTopNewsArticles("IN", currentPage)
        viewModel.newsArticles.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Error -> hideProgressBar()
                is Resource.Loading -> showProgressBar()
                is Resource.Success -> response.data?.let {
                    hideProgressBar()
                    newsListAdapter.differ.submitList(it.articles)
                    val lastPageNo = it.totalResults / 20 + 1
                    isLastPage = currentPage == lastPageNo
                }
            }
        }
    }

    private fun displaySearchResults() {
        viewModel.searchResults.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Error -> {
                    hideProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> response.data?.let {
                    hideProgressBar()
                    newsListAdapter.differ.submitList(it.articles)
                }
            }
        }
    }

    private fun canLoadNextPage(): Boolean {
        val layoutManager = binding.rvNewsFeed.layoutManager as LinearLayoutManager
        val itemsVisibleOnScreen = layoutManager.childCount
        val totalItems = layoutManager.itemCount
        val scrolledOutItems = layoutManager.findFirstVisibleItemPosition()
        val hasScrolledToEnd = itemsVisibleOnScreen + scrolledOutItems >= totalItems

        return !isLoading && !isLastPage && isScrolling && hasScrolledToEnd
    }

    private fun loadNextPage() {
        if (canLoadNextPage()) {
            currentPage++
            viewModel.getTopNewsArticles("IN", currentPage)
            isScrolling = false
        }
    }

    private fun showProgressBar() {
        binding.pbNewsFeed.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideProgressBar() {
        binding.pbNewsFeed.visibility = View.GONE
        isLoading = false
    }

    private fun setSearchViewQueryTextListener(menu: Menu) {
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getSearchResultsForQuery(query ?: "", 1)
                displaySearchResults()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.getSearchResultsForQuery(newText ?: "", 1)
                    displaySearchResults()
                }
                return false
            }
        })
    }

    private fun setSearchViewOnCloseListener(menu: Menu) {
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setOnCloseListener {
            Log.i("MyTag", "Close")
            initRecyclerView()
            displayNewsArticles()
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        setSearchViewQueryTextListener(menu)
        setSearchViewOnCloseListener(menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.search -> {
                true
            }
            else -> false
        }
    }
}