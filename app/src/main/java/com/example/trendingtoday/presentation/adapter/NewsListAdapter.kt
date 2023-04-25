package com.example.trendingtoday.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.trendingtoday.data.model.NewsArticle
import com.example.trendingtoday.databinding.ListItemNewsBinding
import java.text.SimpleDateFormat
import java.util.*

class NewsListAdapter : RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<NewsArticle>() {
        override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val binding =
            ListItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val newsArticle = differ.currentList[position]
        holder.bind(newsArticle)
    }

    override fun getItemCount() = differ.currentList.size

    inner class NewsListViewHolder(private val binding: ListItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(newsArticle: NewsArticle) {
            binding.apply {
                tvArticleTitle.text = newsArticle.title
                tvArticleAuthor.text = newsArticle.author

                if (newsArticle.publishedAt.isNotBlank()) {
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
                    val date = dateFormat.parse(newsArticle.publishedAt)
                    val formatter = SimpleDateFormat("dd MMMM yyyy")
                    tvArticlePublishedAt.text = date?.let { formatter.format(it).toString() }
                }

                if (newsArticle.urlToImage.isNullOrBlank()) {
                    ivArticlePoster.visibility = View.GONE
                } else {
                    Glide.with(binding.ivArticlePoster.context)
                        .load(newsArticle.urlToImage)
                        .centerCrop()
                        .into(binding.ivArticlePoster)
                }

                if (newsArticle.description.isNullOrBlank()) {
                    tvArticleDescription.visibility = View.GONE
                } else {
                    tvArticleDescription.text = newsArticle.description
                }

                root.setOnClickListener { onItemClickListener(newsArticle) }
            }
        }
    }

    private lateinit var onItemClickListener: (NewsArticle) -> Unit
    fun setOnClickListener(listener: (NewsArticle) -> Unit) {
        onItemClickListener = listener
    }
}