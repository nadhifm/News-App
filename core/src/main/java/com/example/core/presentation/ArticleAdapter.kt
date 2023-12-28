package com.example.core.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.R
import com.example.core.databinding.ArticleItemBinding
import com.example.core.domain.models.Article
import com.example.core.utils.Utils

class ArticleAdapter(
    private val clickListener: (Article) -> Unit
) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    class ArticleViewHolder(private val binding: ArticleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article, clickListener: (Article) -> Unit) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .placeholder(R.drawable.rounded_image_bg)
                    .into(ivCover)

                tvAuthor.text =
                    if (article.author != "") "by ${article.author}" else "by ${article.sourceName}"
                tvDate.text = Utils.formatDate(article.publishedAt)
                tvTitle.text = article.title

                root.setOnClickListener {
                    clickListener(article)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = ArticleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article, clickListener)
    }
}