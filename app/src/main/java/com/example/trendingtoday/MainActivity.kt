package com.example.trendingtoday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.trendingtoday.databinding.ActivityMainBinding
import com.example.trendingtoday.presentation.NewsViewModel
import com.example.trendingtoday.presentation.NewsViewModelFactory
import com.example.trendingtoday.presentation.adapter.NewsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var newsListAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.actionBar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        binding.actionBar.setupWithNavController(navController, appBarConfiguration)
        binding.bnvNews.setupWithNavController(navController)

        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }
}